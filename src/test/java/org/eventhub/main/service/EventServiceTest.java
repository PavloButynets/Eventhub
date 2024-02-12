package org.eventhub.main.service;

import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.model.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
class EventServiceImplTest {
    private final EventService eventService;

    @Autowired
    public EventServiceImplTest(EventService eventService) {
        this.eventService = eventService;
    }

    @Test
    public void createEventCheck() {
        EventRequest eventRequest = new EventRequest();

        eventRequest.setTitle("Football school 34");
        eventRequest.setMaxParticipants(12);
        eventRequest.setStartAt(LocalDateTime.of(2024, Month.APRIL, 1, 10, 10, 30));
        eventRequest.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest.setLocation("37.0902 95.7129");
        eventRequest.setOwnerId(10);

        EventResponse eventResponse = eventService.create(eventRequest);

        Assertions.assertNotNull(eventResponse);
        Assertions.assertEquals(eventService.getAll().size(), 4);

        eventService.delete(eventResponse.getId());

    }
}