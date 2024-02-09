package org.eventhub.main.mapper;

import org.eventhub.main.dto.EventDto;
import org.eventhub.main.model.Event;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EventDtoMapper implements Function<Event, EventDto> {

    @Override
    public EventDto apply(Event event) {
        return EventDto.builder()
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
}
