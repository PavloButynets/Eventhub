package org.eventhub.main.service;

import org.eventhub.main.dto.*;
import org.eventhub.main.model.Participant;
import org.eventhub.main.model.ParticipantState;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {
    ParticipantResponse create(ParticipantRequest participantRequest);

    ParticipantResponse addParticipant(UUID participantId, UUID eventId, String token);
    ParticipantResponse readById(UUID id);
    Participant readByIdEntity(UUID id);
    ParticipantResponse readByUserIdInEventById(UUID userId, UUID eventId);
    ParticipantResponse update(ParticipantRequest participantRequest, UUID id);
    void delete(UUID id, UUID eventId, String token);
    void deleteSelf(UUID id, UUID eventId, String token);
    List<ParticipantResponse> getAll();

    List<ParticipantResponse> getAllByEventId(UUID eventId);
    List<ParticipantResponse> getAllJoinedByEventId(UUID eventId);
    List<UserParticipantResponse> getAllUserRequestsByEventId(UUID eventId);
    List<UserParticipantResponse> getUserParticipantsByEventId(UUID eventId);

    ParticipantStateResponse getParticipantState(UUID eventId, UUID userId);
    ParticipantState getState(UUID eventId, UUID userId);
}
