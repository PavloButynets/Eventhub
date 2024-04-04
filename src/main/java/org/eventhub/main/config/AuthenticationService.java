package org.eventhub.main.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.mapper.RegisterMapper;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.PasswordAuthentication;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RegisterMapper registerMapper;

//    public AuthenticationResponce register(UserRequest request) {
//        request.setPassword(passwordEncoder.encode(request.getPassword()));
//        UserResponse userResponse = userService.create(request);
//        var user = userRepository.findByEmail(request.getEmail());
//
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponce.builder()
//                .token(jwtToken)
//                .build();
//    }

    public AuthenticationResponce register(RegisterRequest registerRequest) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        UserRequest userRequest = registerMapper.requestToEntity(registerRequest, new UserRequest());
        UserResponse userResponse = userService.create(userRequest);
        var user = userRepository.findByEmail(userRequest.getEmail());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponce.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponce login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail());

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id", user.getId());

        var jwtToken = jwtService.generateToken(extraClaims, user);
        return AuthenticationResponce.builder()
                .token(jwtToken)
                .build();
    }
}
