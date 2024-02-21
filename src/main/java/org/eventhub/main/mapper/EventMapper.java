package org.eventhub.main.mapper;

import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Category;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.State;
import org.eventhub.main.service.CategoryService;
import org.eventhub.main.service.UserService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EventMapper {
    UserService userService;
    CategoryService categoryService;
    CategoryMapper categoryMapper;
    public EventMapper(UserService userService, CategoryService categoryService, CategoryMapper categoryMapper) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    public EventResponse entityToResponse(Event event) {
        if (event == null) {
            throw new NullEntityReferenceException("Event can't be found");
        }
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
                .categoryResponses(event.getCategories()
                        .stream()
                        .map(categoryMapper::entityToResponse)
                        .collect(Collectors.toList()))

                .ownerId(event.getOwner().getId())
                .build();
    }

    public Event requestToEntity(EventRequest eventRequest, Event event) {
        if(eventRequest == null){
            throw new NullDtoReferenceException("EventRequest can't be null");
        }
        if(event == null){
            throw new NullEntityReferenceException("Event can't be null");
        }
        event.setTitle(eventRequest.getTitle());
        event.setMaxParticipants(eventRequest.getMaxParticipants());
        event.setStartAt(eventRequest.getStartAt());
        event.setExpireAt(eventRequest.getExpireAt());
        event.setDescription(eventRequest.getDescription());
        event.setLocation(eventRequest.getLocation());
        event.setCategories(eventRequest.getCategoryRequests().stream()
                .map(categoryRequest -> categoryService.getByName(categoryRequest.getName()))
                .collect(Collectors.toList()));
        event.setOwner(userService.readByIdEntity(eventRequest.getOwnerId()));
        return event;
    }
}
