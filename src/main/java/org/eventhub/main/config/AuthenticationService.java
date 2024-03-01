package org.eventhub.main.config;

import lombok.RequiredArgsConstructor;
import org.eventhub.main.dto.AuthenticationRequest;
import org.eventhub.main.dto.AuthenticationResponce;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.PasswordAuthentication;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponce register(UserRequest request) {
        return null;
    }

    public AuthenticationResponce login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponce.builder()
                .token(jwtToken)
                .build();
    }

}
