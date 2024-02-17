package org.eventhub.main.controller;

import groovy.util.logging.Slf4j;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping("/api/users")
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
            throw new NullEntityReferenceException("Invalid Input");
        }
        EventResponse response = eventService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



}