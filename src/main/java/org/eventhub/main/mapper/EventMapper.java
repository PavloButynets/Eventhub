package org.eventhub.main.mapper;

import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.model.Event;
import org.springframework.stereotype.Service;

@Service
public class EventMapper {
    public EventResponse entityToResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .maxParticipants(event.getMaxParticipants())
                .createdAt(event.getCreatedAt())
                .startAt(event.getStartAt())
                .expireAt(event.getExpireAt())
                .description(event.getDescription())
                .participantCount(event.getParticipantCount())
                .state(event.getState())
                .location(event.getLocation())
                .photos(event.getPhotos())
                .owner(event.getOwner())
                .participants(event.getParticipants())
                .categories(event.getCategories())
                .build();
    }

    public Event requestToEntity(EventRequest eventRequest, Event event) {
        event.setTitle(eventRequest.getTitle());
        event.setMaxParticipants(eventRequest.getMaxParticipants());
        event.setStartAt(eventRequest.getStartAt());
        event.setExpireAt(eventRequest.getExpireAt());
        event.setDescription(eventRequest.getDescription());
        event.setLocation(eventRequest.getLocation());
        event.setPhotos(eventRequest.getPhotos());
        event.setOwner(eventRequest.getOwner());
        event.setCategories(eventRequest.getCategories());
        return event;
    }
}
