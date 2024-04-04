package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.ParticipantService;
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
    public EventController(EventService eventService, ParticipantService participantService){
        this.eventService = eventService;
        this.participantService = participantService;
    }
    @PostMapping("/{user_id}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventFullInfoResponse> create(@PathVariable("user_id") UUID userId, @Validated @RequestBody EventRequest request,
                                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException("Invalid Input");
        }
        EventFullInfoResponse response = eventService.create(request);

        if(request.isWithOwner()) {
            ParticipantResponse participantResponse = participantService.create(new ParticipantRequest(response.getId(), userId));
            participantService.addParticipant(participantResponse.getId());
            response.setParticipantCount(response.getParticipantCount() + 1);
        }

        log.info("**/created event(id) = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/events/info")
    public ResponseEntity<List<EventFullInfoResponse>> getAllFullInfo(){
        log.info("**/get all events");
        return new ResponseEntity<>(eventService.getAllFullInfo(), HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventResponseXY>> getAll(){
        log.info("**/get all events");
        return new ResponseEntity<>(eventService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{owner_id}/events/{event_id}")
    public ResponseEntity<EventFullInfoResponse> getById(@PathVariable("event_id") UUID eventId){
        EventFullInfoResponse response = eventService.readById(eventId);
        log.info("**/get by id event(id) = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{owner_id}/events/{event_id}")
    public ResponseEntity<EventFullInfoResponse> update(@Validated @RequestBody EventRequest request,
                                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException("Invalid Input");
        }
        EventFullInfoResponse response = eventService.update(request);
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

    @GetMapping("/{user_id}/events")
    public ResponseEntity<List<EventSearchResponse>> getUserEvents(@PathVariable("user_id") UUID userId) {
        List<EventSearchResponse> userEvents = eventService.getUserEvents(userId);
        return new ResponseEntity<>(userEvents, HttpStatus.OK);
    }
}

