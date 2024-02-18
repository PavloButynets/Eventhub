package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.dto.OperationResponse;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.exception.AccessIsDeniedException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.model.Event;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/EventHub/users")
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService){
        this.eventService = eventService;
        this.userService = userService;
    }
    @PostMapping("/{user_id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventResponse> create(@Validated @RequestBody EventRequest request,
                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException("Invalid Input");
        }
        EventResponse response = eventService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/events")
    public List<EventResponse> getAll(){
        return eventService.getAll();
    }
    @GetMapping("/{owner_id}/events/{event_id}")
    public ResponseEntity<EventResponse> getById(@PathVariable("owner_id") long ownerId,
                                                 @PathVariable("event_id") long eventId){
        EventResponse response = eventService.readById(eventId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{owner_id}/events/{event_id}")
    public ResponseEntity<EventResponse> update(@PathVariable("owner_id") long ownerId,
                                                @PathVariable("event_id") long eventId,
                                                @Validated @RequestBody EventRequest request,
                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException("Invalid Input");
        }
        EventResponse response = eventService.update(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{owner_id}/events/{event_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("owner_id") long ownerId,
                                                    @PathVariable("event_id") long eventId) {
        eventService.delete(eventId);
        return new ResponseEntity<>(new OperationResponse("Event deleted successfully"), HttpStatus.OK);
    }
}
