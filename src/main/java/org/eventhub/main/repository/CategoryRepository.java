package org.eventhub.main.repository;

import org.eventhub.main.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category findByName(String name);
    List<Category> findAllByEventsId(UUID eventId);
}
