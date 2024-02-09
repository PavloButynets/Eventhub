package org.eventhub.main.service;

import org.eventhub.main.dto.EventDto;
import org.eventhub.main.dto.EventCreateRequest;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.model.Event;

import java.util.List;

public interface EventService {
    Event create(EventCreateRequest eventCreateRequest);
    EventDto readByIdDto(long id);

    Event readById(long id);
    Event update(EventRequest eventRequest);
    void delete(long id);

    List<EventDto> getAll();
}
