package org.eventhub.main.service;


import org.eventhub.main.dto.EventSearchResponse;
import org.eventhub.main.dto.EventFullInfoResponse;


import java.util.List;

public interface VectorSearchService {

    List<EventSearchResponse> searchEvents(String prompt);

}
