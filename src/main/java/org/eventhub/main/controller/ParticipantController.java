package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.OperationResponse;
import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.exception.AccessIsDeniedException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/api/events/{event_id}/participants")
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
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    List<ParticipantResponse> getAll() {
        return participantService.getAll();
    }

    @GetMapping("/{participant_id}")
    public ResponseEntity<ParticipantResponse> getById(@PathVariable("participant_id")long participantId,
                                                      @PathVariable("event_id")long eventId){
        ParticipantResponse response = participantService.readById(participantId);
        if(eventId != response.getEventId()){
            throw new AccessIsDeniedException("Access is denied");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{participant_id}")
    public ResponseEntity<ParticipantResponse> update(@PathVariable("event_id") long eventId,
                                                      @PathVariable("participant_id") long participantId,
                                                      @Validated @RequestBody ParticipantRequest request,
                                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ResponseStatusException("Invalid Input");
        }
        if(eventId != request.getEventId()){
            throw new AccessIsDeniedException("Access is denied");
        }
        ParticipantResponse response = participantService.update(request, participantId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{participant_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("event_id") long eventId,
                                                    @PathVariable("participant_id") long participantId){
        if(eventId != participantService.readById(participantId).getEventId()){
            throw new AccessIsDeniedException("Access is denied");
        }
        participantService.delete(participantId);
        return new ResponseEntity<>(new OperationResponse("Participant deleted successfully"), HttpStatus.OK);
    }
}
