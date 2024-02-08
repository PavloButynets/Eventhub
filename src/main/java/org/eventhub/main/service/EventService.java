package org.eventhub.main.service;

import org.eventhub.main.model.Event;

import java.util.List;

public interface EventService {
    Event create(Event event);
    Event readById(long id);
    Event update(Event event);
    void delete(long id);

    List<Event> getAll();
    List<Event> getByEventId(long eventId);
}
