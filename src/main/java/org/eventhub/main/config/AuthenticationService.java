package org.eventhub.main.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.mapper.RegisterMapper;
import org.eventhub.main.model.RefreshToken;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.RefreshTokenService;
import org.eventhub.main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.PasswordAuthentication;
import java.util.*;

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
    private final RefreshTokenService refreshTokenService;


    public JwtResponse register(RegisterRequest registerRequest) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        UserRequestCreate userRequest = registerMapper.requestToEntity(registerRequest, new UserRequestCreate());
        UserResponse userResponse = userService.create(userRequest);
        var user = userRepository.findByEmail(userRequest.getEmail());

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());

        var accessToken = jwtService.generateToken(extraClaims, user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(registerRequest.getEmail());

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
