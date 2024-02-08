package org.eventhub.main.service;

import org.eventhub.main.model.EventPhoto;

import java.util.List;

public interface PhotoService {
    EventPhoto create(EventPhoto photo);
    EventPhoto readById(long id);
    EventPhoto update(EventPhoto photo);
    void delete (long id);
    List<EventPhoto> getAll();
}
