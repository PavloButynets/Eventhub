package org.eventhub.main.config;

import lombok.RequiredArgsConstructor;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//@Configuration
//@RequiredArgsConstructor
public class ApplicationConfig {
//
//    private final UserRepository userRepository;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> userRepository.findByEmail(username);
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }
}
