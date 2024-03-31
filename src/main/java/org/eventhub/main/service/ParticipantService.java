package org.eventhub.main.service;

import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.dto.ParticipantWithPhotoResponse;
import org.eventhub.main.model.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface ParticipantService {
    ParticipantResponse create(ParticipantRequest participantRequest);

    ParticipantResponse addParticipant(UUID participantId);
    ParticipantResponse readById(UUID id);
    Participant readByIdEntity(UUID id);
    ParticipantResponse update(ParticipantRequest participantRequest, UUID id);
    void delete(UUID id);
    List<ParticipantResponse> getAll();
    List<ParticipantResponse> getAllByEventId(UUID eventId);
    List<ParticipantResponse> getAllRequestsByEventId(UUID eventId);
}
