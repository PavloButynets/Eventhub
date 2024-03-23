package org.eventhub.main.repository;

import org.eventhub.main.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    Photo findPhotoByPhotoName(String name);
}
