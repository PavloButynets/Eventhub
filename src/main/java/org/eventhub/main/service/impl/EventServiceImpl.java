package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.EventDto;
import org.eventhub.main.dto.EventCreateRequest;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.exception.NotValidDateException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.mapper.EventDtoMapper;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.State;
import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private final EventRepository eventRepository;

    private final EventDtoMapper eventDtoMapper;

    public EventServiceImpl(EventRepository eventRepository, EventDtoMapper eventDtoMapper) {
        this.eventRepository = eventRepository;
        this.eventDtoMapper = eventDtoMapper;
    }


    @Override
    public Event create(EventCreateRequest eventCreateRequest) {
        LocalDateTime currentTime = LocalDateTime.now();
        Event event = new Event();

        mapCreateEventRequest(event, eventCreateRequest);

        event.setCreatedAt(currentTime);
        event.setParticipantCount(0);

        checkState(event,currentTime, eventCreateRequest.getStartAt(), eventCreateRequest.getExpireAt());

        event.setParticipants(new ArrayList<>());

        if(event != null) {
            return eventRepository.save(event);
        }
        throw new NullEntityReferenceException("Cannot save null event");
    }

    @Override
    public EventDto readByIdDto(long id) {
        Event event = eventRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Non existing id: " + id));
        return eventDtoMapper.apply(event);
    }

    @Override
    public Event readById(long id) {
        return eventRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Non existing id: " + id));
    }

    @Override
    @Transactional
    public Event update(EventRequest eventRequest) {
        if(eventRequest != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            Event event = readById(eventRequest.getId());

            mapEventRequest(event, eventRequest);

            checkState(event,currentTime, eventRequest.getStartAt(), eventRequest.getExpireAt());

            return eventRepository.save(event);
        }
        throw new NullEntityReferenceException("Cannot update null event");
    }

    @Override
    @Transactional
    public void delete(long id) {
        eventRepository.delete(readById(id));
    }

    @Override
    public List<EventDto> getAll() {
        return eventRepository.findAll()
                .stream()
                .map(eventDtoMapper)
                .collect(Collectors.toList());
    }

    void checkState(Event event, LocalDateTime currentTime, LocalDateTime startAt, LocalDateTime expireAt) {

        int dateComparisonResultStartAt = currentTime.compareTo(startAt);
        int dateComparisonResultExpireAt = currentTime.compareTo(expireAt);

        //Exceptions
        if (!startAt.isBefore(expireAt)) {
            throw new NotValidDateException("Choose correct date");
        }

        if (dateComparisonResultStartAt < 0) {event.setState(State.UPCOMING);}
        else if (dateComparisonResultExpireAt <= 0) {event.setState(State.LIVE);}
        else {event.setState(State.PAST);}
    }

    void mapEventRequest(Event event, EventRequest eventRequest) {
        event.setTitle(eventRequest.getTitle());
        event.setMaxParticipants(eventRequest.getMaxParticipants());
        event.setStartAt(eventRequest.getStartAt());
        event.setExpireAt(eventRequest.getExpireAt());
        event.setDescription(eventRequest.getDescription());
        event.setLocation(eventRequest.getLocation());
        event.setPhotos(eventRequest.getPhotos());
        event.setOwner(eventRequest.getOwner());
        event.setCategories(eventRequest.getCategories());
    }

    void mapCreateEventRequest(Event event, EventCreateRequest eventCreateRequest) {
        event.setTitle(eventCreateRequest.getTitle());
        event.setMaxParticipants(eventCreateRequest.getMaxParticipants());
        event.setStartAt(eventCreateRequest.getStartAt());
        event.setExpireAt(eventCreateRequest.getExpireAt());
        event.setDescription(eventCreateRequest.getDescription());
        event.setLocation(eventCreateRequest.getLocation());
        event.setPhotos(eventCreateRequest.getPhotos());
        event.setOwner(eventCreateRequest.getOwner());
        event.setCategories(eventCreateRequest.getCategories());
    }
}
