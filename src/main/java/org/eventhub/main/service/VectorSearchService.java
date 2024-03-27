package org.eventhub.main.service;

import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.dto.EventSearchResponse;

import java.util.List;

public interface VectorSearchService {
    List<EventSearchResponse> searchEvents(String prompt);
}
