package org.eventhub.main.repository;

import org.eventhub.main.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {
    @Query("SELECT prt FROM PasswordResetToken prt WHERE prt.token = :token")
    Optional<PasswordResetToken> findByToken(String token);
}
