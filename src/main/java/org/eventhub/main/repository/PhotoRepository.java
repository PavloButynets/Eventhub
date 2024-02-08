package org.eventhub.main.repository;

import org.eventhub.main.model.EventPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<EventPhoto, Long> {
}
