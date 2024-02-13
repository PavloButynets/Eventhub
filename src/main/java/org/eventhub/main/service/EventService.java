package org.eventhub.main.service;

import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.model.Event;

import java.util.List;

public interface EventService {
    EventResponse create(EventRequest eventRequest);
    EventResponse readById(long id);

    Event readByIdEntity(long id);

    Event readByTitle(String title);
    EventResponse update(EventRequest eventRequest);
    void delete(long id);

    List<EventResponse> getAll();
}
