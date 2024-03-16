package org.eventhub.main.service;

import org.eventhub.main.dto.EventResponse;

import java.util.List;

public interface VectorSearchService {
    List<EventResponse> searchEvents(String prompt);
}
