package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.ResponseStatusException;
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

    @Autowired
    public ParticipantController(ParticipantService participantService){
        this.participantService = participantService;
    }

    @PostMapping
    public ResponseEntity<ParticipantResponse> create(@Validated @RequestBody ParticipantRequest request,
                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException("Invalid Input");
        }
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

    @GetMapping
    public ResponseEntity<List<ParticipantResponse>> getByEventId(@PathVariable("event_id") UUID eventId){
        List<ParticipantResponse> responses = participantService.getAllByEventId(eventId);
        log.info("**/get by event id: " + eventId + " participants");

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserParticipantResponse>> getUserParticipantsByEventId(@PathVariable("event_id") UUID eventId){
        List<UserParticipantResponse> responses = participantService.getUserParticipantsByEventId(eventId);
        log.info("**/get user participants by event id: " + eventId);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }



    @GetMapping("/requests")
    public ResponseEntity<List<ParticipantResponse>> getRequestsByEventId(@PathVariable("event_id") UUID eventId){
        List<ParticipantResponse> responses = participantService.getAllRequestsByEventId(eventId);
        log.info("**/get all requests by event id: " + eventId + " participants");

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PutMapping("/{participant_id}")
    public ResponseEntity<ParticipantResponse> update(@PathVariable("participant_id") UUID participantId){

        ParticipantResponse response = participantService.addParticipant(participantId);
        log.info("**/Added participant(id) = " + response.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{participant_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("participant_id") UUID participantId){
        participantService.delete(participantId);
        log.info("**/deleted participant(id) = " + participantId);
        return new ResponseEntity<>(new OperationResponse("Participant deleted successfully"), HttpStatus.OK);
    }
}
