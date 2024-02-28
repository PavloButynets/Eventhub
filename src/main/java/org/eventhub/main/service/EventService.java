package org.eventhub.main.service;

import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.model.Event;

import java.util.List;
import java.util.UUID;

public interface EventService {
    EventResponse create(EventRequest eventRequest);
    EventResponse readById(UUID id);

    Event readByIdEntity(UUID id);

    Event readByTitle(String title);
    EventResponse update(EventRequest eventRequest);
    void delete(UUID id);

    List<EventResponse> getAll();
}
