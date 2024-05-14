package org.eventhub.main.controller;

import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.eventhub.main.config.AuthenticationService;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eventhub.main.model.User;
import org.eventhub.main.service.ConfirmationTokenService;
import org.eventhub.main.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.security.GeneralSecurityException;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@Validated @RequestBody UserRequestCreate userRequest, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            throw new ResponseStatusException(Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }

        User user = this.authService.register(userRequest);
        return new ResponseEntity<>(user.getEmail(), HttpStatus.CREATED);
    }

    @GetMapping("/resend")
    public ResponseEntity<String> resendVerificationEmail(@RequestParam("email") String email) throws IOException {
        authService.resendRegistrationEmail(email);

        log.info("**/resend confirmation email to = " + email);
        return new ResponseEntity<>(email, HttpStatus.CREATED);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<JwtResponse> confirm(@RequestParam("token")String confirmationToken) {
        log.info("**/confirm token(id) = " + confirmationToken);
        return ResponseEntity.ok(authService.confirm(UUID.fromString(confirmationToken)));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/google")
    public ResponseEntity<GoogleJwtResponse> googleAuthentication(@RequestBody GoogleOauthRequest request) throws GeneralSecurityException, IOException {
        log.info("Authorizing with google");
        return ResponseEntity.ok(authService.googleLogin(request));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<OperationResponse> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return new ResponseEntity<>(new OperationResponse("Refresh token deleted successfully"), HttpStatus.OK);
    }
}
