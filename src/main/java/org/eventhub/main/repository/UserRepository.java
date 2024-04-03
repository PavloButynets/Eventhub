package org.eventhub.main.repository;

import org.eventhub.main.model.Event;
import org.eventhub.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    @Query("SELECT e FROM Event e WHERE e.owner.id = :userId OR EXISTS " +
            "(SELECT p FROM Participant p WHERE p.event = e AND p.user.id = :userId)")
    List<Event> findUserEvents(@Param("userId") UUID userId);
}
