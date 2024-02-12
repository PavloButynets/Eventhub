package org.eventhub.main.service;

import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.mapper.EventMapper;
import org.eventhub.main.model.Event;
import org.eventhub.main.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceImplTest {
    private final EventService eventService;

    @Autowired
    public EventServiceImplTest(EventService eventService) {
        this.eventService = eventService;
    }

    public void createEventCheck() {
        EventRequest eventRequest = new EventRequest();

        eventRequest.setTitle("Football school 34");
        eventRequest.setMaxParticipants(12);
        eventRequest.setStartAt(LocalDateTime.of(2024, Month.APRIL, 1, 10, 10, 30));
        eventRequest.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest.setLocation("37.0902 95.7129");
        eventRequest.setOwnerId(10);

        EventResponse event = eventService.create(eventRequest);

    }
}