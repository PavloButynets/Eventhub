package org.eventhub.main.config;

import com.google.api.client.http.HttpTransport;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.AccessIsDeniedException;
import org.eventhub.main.exception.PasswordException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.mapper.RegisterMapper;
import org.eventhub.main.model.ConfirmationToken;
import org.eventhub.main.model.PasswordResetToken;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.*;

import org.eventhub.main.model.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.JsonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RegisterMapper registerMapper;

    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;
    private final ThreadPoolTaskScheduler scheduler;

    private final RefreshTokenService refreshTokenService;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Value("${google.clientId}")
    private String googleClientId;

    private void scheduleConfirmationTask(String email) {
        int timeForVerification = 110;
        scheduler.schedule(() -> {
            User user = userService.findByEmail(email);
            if (!user.isVerified()) {
                userService.delete(user.getId());
            }

        }, Instant.now().plusSeconds(timeForVerification));
    }

    public User register(UserRequestCreate registerRequest) throws IOException {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        UserResponse userResponse = userService.create(registerRequest);

        User user = userService.findByEmail(userResponse.getEmail());
        ConfirmationToken confirmationToken = confirmationTokenService.create(user);
        
        EmailRequest emailRequest = new EmailRequest(registerRequest.getEmail(),"Verify email", "Please, verify your email", registerRequest.getFirstName());
        emailService.sendVerificationEmail(confirmationToken.getToken(), emailRequest);

        scheduleConfirmationTask(userResponse.getEmail());
        return user;
    }

    public void resendRegistrationEmail(String email) throws IOException {
        User user = userService.findByEmail(email);

        if(user.isVerified()){
            throw new AccessIsDeniedException("User is already verified!");
        }

        EmailRequest emailRequest = new EmailRequest(email, "Verify email", "Please, verify your email", user.getFirstName());
        emailService.sendVerificationEmail(user.getConfirmationToken().getToken(), emailRequest);
    }

    public JwtResponse confirm(String confirmationToken){
        ConfirmationToken token = this.confirmationTokenService.findByToken(confirmationToken);
        if(token.isExpired()){
            this.confirmationTokenService.delete(token.getId());
            throw new ResponseStatusException("Token is not valid!");
        }
        UUID userId = token.getUser().getId();

        userService.confirmUser(userId);
        confirmationTokenService.delete(token.getId());

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", userId);

        var accessToken = jwtService.generateToken(extraClaims, token.getUser());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(token.getUser().getEmail());

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiryDate(jwtService.expDate(accessToken))
                .build();
    }

    public void resetPassword(String email) throws IOException {
        User user = this.userService.findByEmail(email);
        if(user.getPasswordResetToken() == null) {
           PasswordResetToken token = passwordResetTokenService.create(user);
           user.setPasswordResetToken(token);
        }

        EmailRequest emailRequest = new EmailRequest(email,"Reset password", "Please, reset your password", user.getFirstName());
        emailService.sendResetPasswordEmail(user.getPasswordResetToken().getToken(),emailRequest);
    }
    public JwtResponse confirmResetPassword(PasswordResetRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        PasswordResetToken token = passwordResetTokenService.findByToken(request.getToken());
        if(token.isExpired()){
            passwordResetTokenService.delete(token.getId());
            throw new PasswordException("Token is not valid!");
        }

        User user = token.getUser();
        if(encoder.matches(request.getNewPassword(),user.getPassword())){
            throw new PasswordException("A new password cannot be the same as the old one!");
        }

        String newPassword = encoder.encode(request.getNewPassword());
        user.setPassword(newPassword);
        passwordResetTokenService.delete(token.getId());

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());

        var accessToken = jwtService.generateToken(extraClaims, user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiryDate(jwtService.expDate(accessToken))
                .build();
    }
    public JwtResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail());
        if(!user.isVerified()){
            throw new AccessIsDeniedException("Your account is not verified yet!");
        }

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());

        var accessToken = jwtService.generateToken(extraClaims, user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .expiryDate(jwtService.expDate(accessToken))
                .build();
    }
    public GoogleJwtResponse googleLogin(GoogleOauthRequest request) throws GeneralSecurityException, IOException {
        HttpTransport transport = new com.google.api.client.http.javanet.NetHttpTransport();
        JsonFactory jsonFactory = com.google.api.client.json.gson.GsonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        logger.info("Verified google client");

        GoogleIdToken idToken = verifier.verify(request.getGoogleToken());
        if (idToken != null) {
            Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            User user = userRepository.findByEmail(email);
            if (user != null) {
                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("id", user.getId());

                String accessToken = jwtService.generateToken(extraClaims, user);
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);
                return GoogleJwtResponse.builder()
                        .email(email)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getToken())
                        .expiryDate(jwtService.expDate(accessToken))
                        .build();
            }

            boolean emailVerified = payload.getEmailVerified();
            RegisterRequest registerRequest = registerMapper.googlePayloadToRegisterRequest(payload);
            UserRequestCreate userRequest = registerMapper.requestToEntity(registerRequest, new UserRequestCreate());

            if (emailVerified) {
                logger.info("Registering verified(email) user");

                userService.create(userRequest);
                User userToCreate = userRepository.findByEmail(email);

                Map<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("id", userToCreate.getId());

                String accessToken = jwtService.generateToken(extraClaims, userToCreate);
                RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);
                return GoogleJwtResponse.builder()
                        .email(email)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getToken())
                        .expiryDate(jwtService.expDate(accessToken))
                        .build();
            }

            register(userRequest);
            return GoogleJwtResponse.builder()
                    .email(email)
                    .build();


        } else {
            throw new AccessIsDeniedException("Invalid ID token.");
        }
    }

    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    Map<String, Object> extraClaims = new HashMap<>();
                    extraClaims.put("id", user.getId());

                    String accessToken = jwtService.generateToken(extraClaims, user);
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequest.getToken())
                            .expiryDate(jwtService.expDate(accessToken))
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database"));
    }

    public void logout(String token) {
        refreshTokenService.deleteTokenByUserId(jwtService.getId(token));
    }
}
