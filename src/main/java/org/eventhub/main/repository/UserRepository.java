package org.eventhub.main.repository;

import org.eventhub.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
