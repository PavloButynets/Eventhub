package org.eventhub.main.service;

import org.eventhub.main.model.Photo;

import java.util.List;

public interface PhotoService {
    Photo create(Photo photo);
    Photo readById(long id);
    Photo update(Photo photo);
    void delete (long id);
    List<Photo> getAll();
}
