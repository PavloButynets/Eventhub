package org.eventhub.main.service.impl;

import org.eventhub.main.config.JwtService;
import org.eventhub.main.model.RefreshToken;
import org.eventhub.main.repository.RefreshTokenRepository;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RefreshToken createRefreshToken(String username) {
        var user = userRepository.findByEmail(username);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(Date.from(Instant.now())) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired . Please make a sigh in request");
        }
        return token;   
    }

    @Override
    public void deleteTokenByUserId(UUID userId) {
        refreshTokenRepository.delete(refreshTokenRepository.findRefreshTokenByUserId(userId));
    }
}
