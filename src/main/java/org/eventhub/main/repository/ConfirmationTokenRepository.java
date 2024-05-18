package org.eventhub.main.repository;

import org.eventhub.main.model.ConfirmationToken;
import org.eventhub.main.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, UUID> {
    @Query("SELECT ct FROM ConfirmationToken ct WHERE ct.token = :token")
    Optional<ConfirmationToken> findByToken(String token);
}
