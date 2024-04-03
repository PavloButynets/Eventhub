package org.eventhub.main.service;

import org.eventhub.main.dto.*;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.Photo;

import java.util.List;
import java.util.UUID;

public interface EventService {
    EventFullInfoResponse create(EventRequest eventRequest);
    EventFullInfoResponse readById(UUID id);
    Event readByIdEntity(UUID id);
    Event readByTitle(String title);
    EventFullInfoResponse update(EventRequest eventRequest);
    void delete(UUID id);
    List<EventFullInfoResponse> getAllFullInfo();
    List<EventSearchResponse> getAllSearchResponse();
    List<Event> getAllEntities();
    List<EventResponseXY> getAll();
    void addImage(UUID eventId, Photo image);
    void deleteImage(UUID eventId, Photo image);
    EventSearchResponse readByIdSearch(UUID eventId);

}
