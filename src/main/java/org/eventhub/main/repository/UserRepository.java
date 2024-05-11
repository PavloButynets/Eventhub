package org.eventhub.main.repository;

import org.eventhub.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(String username);

    @Query("SELECT DISTINCT p.user FROM Participant p " +
            "WHERE p.event.id = :eventId AND p.isApproved = true")
    List<User> findApprovedUsersByEventId(UUID eventId);

}
