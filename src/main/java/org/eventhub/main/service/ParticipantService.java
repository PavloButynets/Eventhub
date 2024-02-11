package org.eventhub.main.service;

import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.model.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Predicate;

public interface ParticipantService {
    ParticipantResponse create(ParticipantRequest participantRequest);
    ParticipantResponse readById(long id);
    Participant readByIdEntity(long id);
    ParticipantResponse update(ParticipantRequest participantRequest, long id);
    void delete(Long id);
    List<ParticipantResponse> getAll();
}
