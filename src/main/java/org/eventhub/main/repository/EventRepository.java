package org.eventhub.main.repository;

import org.eventhub.main.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByTitle(String title);
}
