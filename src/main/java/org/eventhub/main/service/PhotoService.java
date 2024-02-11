package org.eventhub.main.service;

import org.eventhub.main.dto.EventPhotoRequest;
import org.eventhub.main.dto.EventPhotoResponse;
import org.eventhub.main.model.EventPhoto;

import java.util.List;

public interface PhotoService {
    EventPhotoResponse create(EventPhotoRequest photoRequest);
    EventPhotoResponse readById(long id);
    EventPhoto readByIdEntity(long id);
    EventPhotoResponse update(EventPhotoRequest photoRequest, long id);
    void delete (long id);
    List<EventPhotoResponse> getAll();
}
