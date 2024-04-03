package org.eventhub.main.service;

import org.eventhub.main.dto.EventFilterRequest;
import org.eventhub.main.dto.EventSearchResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FilterService {
    List<EventSearchResponse> filterEvents(EventFilterRequest filterRequest);
}
