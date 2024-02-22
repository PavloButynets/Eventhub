package org.eventhub.main.repository;

import org.eventhub.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.OffsetTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Optional<User> findByEmail(String email);
    User findByEmail(String email);
}
