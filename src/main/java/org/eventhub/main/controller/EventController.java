package org.eventhub.main.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.AccessIsDeniedException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.Participant;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.ParticipantService;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/users")
public class EventController {
    private final EventService eventService;
    private final ParticipantService participantService;

    @Autowired
    public EventController(EventService eventService, UserService userService, ParticipantService participantService){
        this.eventService = eventService;
        this.participantService = participantService;
    }
    @PostMapping("/{user_id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventResponse> create(@RequestParam("count") int count, @RequestParam("with_owner") boolean withOwner, @PathVariable("user_id") UUID userId, @Validated @RequestBody EventRequest request,
                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException("Invalid Input");
        }
        EventResponse response = eventService.create(request, count);

        if(withOwner) {
            participantService.create(new ParticipantRequest(response.getId(), userId));
        }
        log.info("**/created event(id) = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> getAll(){
        log.info("**/get all events");
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.OK);
    }
    @GetMapping("/{owner_id}/events/{event_id}")
    public ResponseEntity<EventResponse> getById(@PathVariable("event_id") UUID eventId){
        EventResponse response = eventService.readById(eventId);
        log.info("**/get by id event(id) = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{owner_id}/events/{event_id}")
    public ResponseEntity<EventResponse> update(@Validated @RequestBody EventRequest request,
                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException("Invalid Input");
        }
        EventResponse response = eventService.update(request);
        log.info("**/updated event(id) = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{owner_id}/events/{event_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("event_id") UUID eventId) {
        String title = eventService.readById(eventId).getTitle();
        eventService.delete(eventId);
        log.info("**/deleted event(id) = " + eventId);

        return new ResponseEntity<>(new OperationResponse("Event with title '"+title+"' deleted successfully"), HttpStatus.OK);
    }
}

