package org.eventhub.main.service;

import org.eventhub.main.dto.EventFullInfoResponse;

import java.util.List;

public interface VectorSearchService {
    List<EventFullInfoResponse> searchEvents(String prompt);
}
