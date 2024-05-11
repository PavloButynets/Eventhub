package org.eventhub.main.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.AccessIsDeniedException;
import org.eventhub.main.model.ConfirmationToken;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.ConfirmationTokenService;
import org.eventhub.main.service.EmailService;

import org.eventhub.main.model.RefreshToken;
import org.eventhub.main.service.RefreshTokenService;
import org.eventhub.main.service.UserService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

import java.util.*;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final ThreadPoolTaskScheduler scheduler;

    private final RefreshTokenService refreshTokenService;
    private final Map<String, ScheduledFuture<?>> confirmationTasks = new HashMap<>();


    private void scheduleConfirmationTask(String email) {
        int timeForVerification = 68;
        ScheduledFuture<?> task = scheduler.schedule(() -> {
            User user = userService.findByEmail(email);
            if (!user.isVerified()) {
                userService.delete(user.getId());
            }

            confirmationTasks.remove(email);
        }, Instant.now().plusSeconds(timeForVerification));

        confirmationTasks.put(email, task);
    }

    private void cancelConfirmationTask(String email) {
        ScheduledFuture<?> task = confirmationTasks.get(email);
        if (task != null && !task.isDone()) {
            task.cancel(true);
            confirmationTasks.remove(email);
        }
    }

    public User register(UserRequestCreate registerRequest) throws IOException {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        UserResponse userResponse = userService.create(registerRequest);

        User user = userService.findByEmail(userResponse.getEmail());
        ConfirmationToken confirmationToken = confirmationTokenService.create(user);
        
        EmailRequest emailRequest = new EmailRequest(registerRequest.getEmail(),"Verify email", "Please, verify your email", registerRequest.getFirstName());
        emailService.sendVerificationEmail(confirmationToken.getId(), emailRequest);

        scheduleConfirmationTask(userResponse.getEmail());
        return user;
    }

    public void resendRegistrationEmail(String email) throws IOException {
        User user = userService.findByEmail(email);

        if(user.isVerified()){
            throw new AccessIsDeniedException("User is already verified!");
        }

        EmailRequest emailRequest = new EmailRequest(email, "Verify email", "Please, verify your email", user.getFirstName());
        emailService.sendVerificationEmail(user.getConfirmationToken().getId(), emailRequest);

        cancelConfirmationTask(email);
        scheduleConfirmationTask(email);
    }

    public JwtResponse confirm(UUID confirmationTokenId){
        ConfirmationToken token = this.confirmationTokenService.read(confirmationTokenId);
        UUID userId = token.getUser().getId();

        userService.confirmUser(userId);

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
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database"));
    }

    public void logout(String token) {
        refreshTokenService.deleteTokenByUserId(jwtService.getId(token));
    }
}
