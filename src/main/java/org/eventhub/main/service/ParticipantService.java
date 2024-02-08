package org.eventhub.main.service;

import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.function.Predicate;

public interface ParticipantService {
    ParticipantResponse create(ParticipantRequest participantRequest);
    ParticipantResponse getById(Long id);
    Page<ParticipantResponse> getAll(Predicate filters, Pageable pageable);
    void delete(Long id);
}
