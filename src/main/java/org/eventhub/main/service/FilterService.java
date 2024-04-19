package org.eventhub.main.service;

import org.eventhub.main.dto.CheckboxRequest;
import org.eventhub.main.dto.EventFilterRequest;
import org.eventhub.main.dto.EventResponseXY;
import org.eventhub.main.dto.EventSearchResponse;
import org.eventhub.main.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FilterService {
    List<EventSearchResponse> filterEvents(EventFilterRequest filterRequest);
    Set<EventSearchResponse> filterCheckboxEvents(CheckboxRequest checkboxRequest);

    List<EventResponseXY> allLiveAndUpcomingEvents();
}
