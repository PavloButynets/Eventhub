package org.eventhub.main.mapper;

import org.eventhub.main.dto.CategoryRequest;
import org.eventhub.main.dto.CategoryResponse;
import org.eventhub.main.dto.EventPhotoRequest;
import org.eventhub.main.dto.EventPhotoResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Category;
import org.eventhub.main.model.EventPhoto;
import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventPhotoMapper {
    private final EventRepository eventRepository;

    private final String sasToken;
    @Autowired
    public EventPhotoMapper(EventRepository eventRepository){
        this.eventRepository = eventRepository;
        this.sasToken = System.getenv("sas_token");
    }
    public EventPhotoResponse entityToResponse(EventPhoto eventPhoto) {
        if (eventPhoto == null) {
            throw new NullEntityReferenceException("Event Photo can't be null");
        }
        return EventPhotoResponse.builder()
                .id(eventPhoto.getId())
                .photoName(eventPhoto.getPhotoName())
                .photoUrl(eventPhoto.getPhotoUrl() +"?"+sasToken)
                .eventId(eventPhoto.getEvent().getId())
                .build();
    }

    public EventPhoto requestToEntity(EventPhotoRequest request, EventPhoto eventPhoto){
        if(request == null){
            throw new NullDtoReferenceException("Request can't be null");
        }
        if(eventPhoto == null){
            throw new NullEntityReferenceException("Event Photo can't be null");
        }

        eventPhoto.setPhotoUrl(request.getPhotoUrl());
        eventPhoto.setPhotoName(request.getPhotoName());
        eventPhoto.setEvent(eventRepository.findById(request.getEventId()).orElseThrow(()->new NullEntityReferenceException("Event can't be null")));
        return eventPhoto;
    }

}
