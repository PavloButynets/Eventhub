package org.eventhub.main.repository;

import org.eventhub.main.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    List<Category> findAllByEventsId(Long eventId);
}
