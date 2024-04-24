package org.eventhub.main.repository;

import org.eventhub.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(String username);
}
