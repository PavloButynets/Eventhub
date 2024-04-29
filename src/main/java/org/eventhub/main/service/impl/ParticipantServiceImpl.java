package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.config.JwtService;
import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.dto.ParticipantStateResponse;
import org.eventhub.main.dto.UserParticipantResponse;
import org.eventhub.main.exception.AccessIsDeniedException;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.mapper.ParticipantMapper;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.Participant;
import org.eventhub.main.model.ParticipantState;
import org.eventhub.main.repository.ParticipantRepository;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {


    private final ParticipantRepository participantRepository;
    private final ParticipantMapper participantMapper;
    private final EventService eventService;
    private final JwtService jwtService;


    @Autowired
    public ParticipantServiceImpl(ParticipantRepository participantRepository, ParticipantMapper participantMapper, EventService eventService, JwtService jwtService) {
        this.participantRepository = participantRepository;
        this.participantMapper = participantMapper;
        this.eventService = eventService;
        this.jwtService = jwtService;
    }


    @Override
    public ParticipantResponse create(ParticipantRequest participantRequest) {
        Event event = eventService.readByIdEntity(participantRequest.getEventId());

        if (event.getParticipantCount() >= event.getMaxParticipants()) {
            throw new AccessIsDeniedException("Event " + event.getTitle() + " is full.");
        }

        for (Participant participant1 : event.getParticipants()) {
            if (participant1.getUser().getId().equals(participantRequest.getUserId())) {
                throw new AccessIsDeniedException("Duplicate join-request for " + event.getTitle());
            }
        }

        Participant participant = participantMapper.requestToEntity(participantRequest, new Participant());
        participant.setCreatedAt(LocalDateTime.now());
        participant.setApproved(false);

        return participantMapper.entityToResponse( participantRepository.save(participant));
    }

    @Override
    public ParticipantResponse addParticipant(UUID participantId, UUID eventId, String token) {
        eventService.validateEventOwner(token, eventId);

        Participant existingParticipant = readByIdEntity(participantId);

        Event event = existingParticipant.getEvent();

        if (event.getParticipantCount() >= event.getMaxParticipants()) {
            throw new AccessIsDeniedException("Event " + event.getTitle() + " is full.");
        }
        System.out.println("Participant count: " + event.getParticipantCount());
        event.setParticipantCount(event.getParticipantCount() + 1);
        System.out.println("Participant count: " + event.getParticipantCount());
        existingParticipant.setApproved(true);

        return participantMapper.entityToResponse(participantRepository.save(existingParticipant));
    }

    @Override
    public ParticipantResponse readById(UUID id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Participant with " + id + " id is not found"));
        return participantMapper.entityToResponse(participant);
    }
    @Override
    public ParticipantResponse readByUserIdInEventById(UUID userId, UUID eventId) {
        return getAllByEventId(eventId).stream().filter(participantRes -> participantRes.getUserId().equals(userId)).findFirst()
                .orElseThrow(()->new EntityNotFoundException("Participant with user id:  " + userId + " is not found in event with id: " + eventId));
    }
    @Override
    public Participant readByIdEntity(UUID id){
        return participantRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Participant with" + id + " id is not found"));
    }
    @Override
    public ParticipantResponse update(ParticipantRequest participantRequest, UUID id) {
        if(participantRequest != null){
            Participant existingParticipant = readByIdEntity(id);
            Participant participant = participantMapper.requestToEntity(participantRequest, existingParticipant);
            participant = participantRepository.save(participant);
            return participantMapper.entityToResponse(participant);
        }
        throw new NullDtoReferenceException("Request can't be null");
    }

    @Override
    public void delete(UUID id, UUID eventId, String token) {
        eventService.validateEventOwner(token, eventId);

        Participant participant = readByIdEntity(id);

        Event event = eventService.readByIdEntity(eventId);
        if (participant.isApproved()) {
            event.setParticipantCount(event.getParticipantCount() - 1);
        }

        participantRepository.delete(readByIdEntity(id));
    }
    @Override
    public void deleteSelf(UUID id, UUID eventId, String token) {
        UUID userId = jwtService.getId(token);
        Participant participant = readByIdEntity(id);
        if (participant.getUser().getId().equals(userId) && participant.isApproved()) {
            Event event = eventService.readByIdEntity(eventId);
            event.setParticipantCount(event.getParticipantCount() - 1);
            participantRepository.delete(readByIdEntity(id));
        }
        else {
            throw new AccessIsDeniedException("Not valid leave/delete operation");
        }

    }

    @Override
    public List<ParticipantResponse> getAll() {
        return participantRepository.findAll()
                .stream()
                .map(participantMapper::entityToResponse)
                .collect(Collectors.toList());
    }


    @Override
    public List<ParticipantResponse> getAllByEventId(UUID eventId) {
        Event event = eventService.readByIdEntity(eventId);
        return event.getParticipants()
                .stream()
                .map(participantMapper::entityToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<ParticipantResponse> getAllJoinedByEventId(UUID eventId) {
        Event event = eventService.readByIdEntity(eventId);
        return event.getParticipants()
                .stream()
                .filter(Participant::isApproved)
                .map(participantMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserParticipantResponse> getUserParticipantsByEventId(UUID eventId) {
        Event event = eventService.readByIdEntity(eventId);
        return event.getParticipants()
                .stream()
                .filter(Participant::isApproved)
                .map(participantMapper::entityToUserParticipantResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserParticipantResponse> getAllUserRequestsByEventId(UUID eventId) {
        Event event = eventService.readByIdEntity(eventId);
        return event.getParticipants()
                .stream()
                .filter(participant -> !participant.isApproved())
                .map(participantMapper::entityToUserParticipantResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipantStateResponse getParticipantState(UUID userId, UUID eventId) {
        Event event = eventService.readByIdEntity(eventId);

        ParticipantState state = getState(userId, eventId);
        boolean isOwner = (userId.equals(event.getOwner().getId()));

        return new ParticipantStateResponse(state, isOwner);

    }
    @Override
    public ParticipantState getState(UUID userId, UUID eventId) {
        if (getAllJoinedByEventId(eventId).stream().anyMatch(participant -> participant.getUserId().equals(userId))) {
            return ParticipantState.JOINED;
        } else if (getAllUserRequestsByEventId(eventId).stream().anyMatch(participant -> participant.getUserId().equals(userId))) {
            return ParticipantState.REQUESTED;
        } else {
            return ParticipantState.NONE;
        }

    }
}
