package org.eventhub.main.repository;

import org.eventhub.main.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.user.id = :userId")
    RefreshToken findRefreshTokenByUserId(@Param("userId") UUID userId);
}
