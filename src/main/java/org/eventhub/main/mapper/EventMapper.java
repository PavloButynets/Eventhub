package org.eventhub.main.mapper;

import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.State;
import org.eventhub.main.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class EventMapper {
    UserService userService;

    public EventMapper(UserService userService) {
        this.userService = userService;
    }

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
                .ownerId(event.getOwner().getId())
                .build();
    }

    public Event requestToEntity(EventRequest eventRequest, Event event) {
        event.setTitle(eventRequest.getTitle());
        event.setMaxParticipants(eventRequest.getMaxParticipants());
        event.setStartAt(eventRequest.getStartAt());
        event.setExpireAt(eventRequest.getExpireAt());
        event.setDescription(eventRequest.getDescription());
        event.setLocation(eventRequest.getLocation());
        event.setOwner(userService.readByIdEntity(eventRequest.getOwnerId()));
        return event;
    }
}
