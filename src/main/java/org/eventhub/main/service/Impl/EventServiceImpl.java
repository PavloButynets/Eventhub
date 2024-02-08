package org.eventhub.main.service.Impl;

import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.service.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;

}
