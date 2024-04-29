package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.config.JwtService;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.model.ParticipantState;
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
@RequestMapping("/events/{event_id}/participants")
public class ParticipantController {
    private final ParticipantService participantService;

    private final JwtService jwtService;

    @Autowired
    public ParticipantController(ParticipantService participantService, JwtService jwtService){
        this.participantService = participantService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create")
    public ResponseEntity<ParticipantResponse> create(@PathVariable("event_id") UUID eventId, @RequestHeader (name="Authorization") String token) {
        UUID userId = jwtService.getId(token);

        ParticipantRequest request = new ParticipantRequest(eventId, userId);

        ParticipantResponse response = participantService.create(request);
        log.info("**/created participant(id) = " + response.getId());


        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    ResponseEntity<List<ParticipantResponse>> getAll() {
        log.info("**/get all participant");
        return new ResponseEntity<>(participantService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{participant_id}")
    public ResponseEntity<ParticipantResponse> getById(@PathVariable("participant_id") UUID participantId){
        ParticipantResponse response = participantService.readById(participantId);
        log.info("**/get by id participant(id) = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<ParticipantResponse> getByUser(@PathVariable("event_id") UUID eventId, @RequestHeader (name="Authorization") String token) {
        UUID userId = jwtService.getId(token);
        ParticipantResponse response = participantService.readByUserIdInEventById(userId, eventId);
        log.info("**/get by user id: " + userId + "in event by id: " + eventId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ParticipantResponse>> getAllByEventId(@PathVariable("event_id") UUID eventId){
        List<ParticipantResponse> responses = participantService.getAllByEventId(eventId);
        log.info("**/get by event id: " + eventId + " participants");

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/joined")
    public ResponseEntity<List<ParticipantResponse>> getJoinedByEventId(@PathVariable("event_id") UUID eventId){
        List<ParticipantResponse> responses = participantService.getAllJoinedByEventId(eventId);
        log.info("**/get all joined by event id: " + eventId + " participants");

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserParticipantResponse>> getUserParticipantsByEventId(@PathVariable("event_id") UUID eventId){
        List<UserParticipantResponse> responses = participantService.getUserParticipantsByEventId(eventId);
        log.info("**/get user participants by event id: " + eventId);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/user_state")
    public ResponseEntity<ParticipantStateResponse> getParticipantState(@PathVariable("event_id") UUID eventId, @RequestHeader (name="Authorization") String token) {
        UUID userId = jwtService.getId(token);
        log.info("**/get participant state with user id:" + userId + ", event id: " + eventId);

        return new ResponseEntity<>(participantService.getParticipantState(userId, eventId), HttpStatus.OK);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<UserParticipantResponse>> getUserRequestsByEventId(@PathVariable("event_id") UUID eventId){
        List<UserParticipantResponse> responses = participantService.getAllUserRequestsByEventId(eventId);
        log.info("**/get all user requests by event id: " + eventId + " participants");

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping("/add/{participant_id}")
    public ResponseEntity<ParticipantResponse> addParticipant(@PathVariable("participant_id") UUID participantId, @PathVariable("event_id") UUID eventId, @RequestHeader (name="Authorization") String token) {

        ParticipantResponse response = participantService.addParticipant(participantId, eventId, token);
        log.info("**/Added participant(id) = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{participant_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("participant_id") UUID participantId, @PathVariable("event_id") UUID eventId, @RequestHeader (name="Authorization") String token){
        participantService.delete(participantId, eventId, token);
        log.info("**/deleted participant(id) = " + participantId);
        return new ResponseEntity<>(new OperationResponse("Participant deleted successfully"), HttpStatus.OK);
    }
    @DeleteMapping("/{participant_id}/leave")
    public ResponseEntity<OperationResponse> leave(@PathVariable("participant_id") UUID participantId, @PathVariable("event_id") UUID eventId, @RequestHeader (name="Authorization") String token){
        participantService.deleteSelf(participantId, eventId, token);
        log.info("**/participant(id) = " + participantId + " has left");
        return new ResponseEntity<>(new OperationResponse("Participant has left successfully"), HttpStatus.OK);
    }
}
