package org.eventhub.main.controller;

import lombok.RequiredArgsConstructor;
import org.eventhub.main.config.AuthenticationService;
import org.eventhub.main.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.login(request));
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
