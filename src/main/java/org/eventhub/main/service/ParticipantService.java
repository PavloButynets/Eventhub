package org.eventhub.main.service;

import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.function.Predicate;

public interface ParticipantService {

    void delete(Long id);
}
