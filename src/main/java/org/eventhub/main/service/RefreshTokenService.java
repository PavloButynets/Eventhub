package org.eventhub.main.service;

import org.eventhub.main.model.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String username);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    void deleteTokenByUserId(UUID userId);
}
