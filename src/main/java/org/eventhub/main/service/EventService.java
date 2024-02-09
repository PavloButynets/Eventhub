package org.eventhub.main.service;

import org.eventhub.main.dto.EventDto;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.model.Event;

import java.util.List;

public interface EventService {
    Event create(EventRequest event);
    Event readById(long id);
    Event update(Event event);
    void delete(long id);

    List<EventDto> getAll();
}
