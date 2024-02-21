package org.eventhub.main.service;

import org.eventhub.main.dto.EventPhotoRequest;
import org.eventhub.main.dto.EventPhotoResponse;
import org.eventhub.main.model.EventPhoto;

import java.util.List;
import java.util.UUID;

public interface PhotoService {
    EventPhotoResponse create(EventPhotoRequest photoRequest);
    EventPhotoResponse readById(UUID id);
    EventPhoto readByIdEntity(UUID id);
    EventPhotoResponse update(EventPhotoRequest photoRequest, UUID id);
    void delete (UUID id);
    List<EventPhotoResponse> getAll();
}
