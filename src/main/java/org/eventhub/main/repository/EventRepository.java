package org.eventhub.main.repository;

import org.eventhub.main.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    Event findByTitle(String title);

    @Query("SELECT e FROM Event e WHERE e.owner.id = :userId OR EXISTS " +
            "(SELECT p FROM Participant p WHERE p.event = e AND p.user.id = :userId)")
    List<Event> findUserEvents(@Param("userId") UUID userId);
}
