package org.eventhub.main.service;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.model.Event;

import java.util.List;

public interface EventService {
    Event create(EventRequest eventRequest);
    EventResponse readByIdDto(long id);

    Event readById(long id);

    Event readByTitle(String title);
    Event update(EventRequest eventRequest);
    void delete(long id);

    List<EventResponse> getAll();
}
