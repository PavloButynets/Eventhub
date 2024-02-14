package org.eventhub.main.service;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.EventRequest;
import org.eventhub.main.dto.EventResponse;
import org.eventhub.main.exception.NotValidDateException;
import org.eventhub.main.mapper.EventMapper;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;


@SpringBootTest
class EventServiceTest {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @Autowired



    public EventServiceTest(EventService eventService, EventMapper eventMapper) {

        this.eventService = eventService;
        this.eventMapper = eventMapper;
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


        EventResponse actual = eventService.create(eventRequest);

        Event event = eventMapper.requestToEntity(eventRequest, new Event());
        EventResponse expected = eventMapper.entityToResponse(event);
        expected.setCreatedAt(actual.getCreatedAt());
        expected.setId(actual.getId());
        expected.setState(actual.getState());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(eventService.getAll().size(), 4);
        Assertions.assertEquals(expected, actual);


        eventService.delete(actual.getId());
    }

    @Test
    public void checkInvalidDateEvent() {
        EventRequest eventRequest = new EventRequest();

        eventRequest.setTitle("Football school 34");
        eventRequest.setMaxParticipants(12);
        eventRequest.setStartAt(LocalDateTime.of(2025, Month.APRIL, 1, 10, 10, 30));
        eventRequest.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest.setLocation("37.0902 95.7129");
        eventRequest.setOwnerId(10);

        Assertions.assertThrows(NotValidDateException.class,
                () -> {
                    EventResponse actual = eventService.create(eventRequest);
                });


    }

    @Test
    public void checkStateInEventCreate() {
        EventRequest eventRequest = new EventRequest();

        eventRequest.setTitle("Football school 34");
        eventRequest.setMaxParticipants(12);
        eventRequest.setStartAt(LocalDateTime.of(2024, Month.APRIL, 1, 10, 10, 30));
        eventRequest.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest.setLocation("37.0902 95.7129");
        eventRequest.setOwnerId(10);

        EventRequest eventRequest2 = new EventRequest();

        eventRequest2.setTitle("Football2 school 34");
        eventRequest2.setMaxParticipants(12);
        eventRequest2.setStartAt(LocalDateTime.of(2023, Month.APRIL, 1, 10, 10, 30));
        eventRequest2.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest2.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest2.setLocation("37.0902 95.7129");
        eventRequest2.setOwnerId(10);

        EventRequest eventRequest3 = new EventRequest();

        eventRequest3.setTitle("Football3 school 34");
        eventRequest3.setMaxParticipants(12);
        eventRequest3.setStartAt(LocalDateTime.of(2023, Month.APRIL, 1, 10, 10, 30));
        eventRequest3.setExpireAt(LocalDateTime.of(2023, Month.APRIL, 1, 12, 10, 30));
        eventRequest3.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest3.setLocation("37.0902 95.7129");
        eventRequest3.setOwnerId(10);

        EventResponse actual = eventService.create(eventRequest);

        EventResponse actual2 = eventService.create(eventRequest2);

        EventResponse actual3 = eventService.create(eventRequest3);

        Assertions.assertEquals(State.UPCOMING, actual.getState());
        Assertions.assertEquals(State.LIVE, actual2.getState());
        Assertions.assertEquals(State.PAST, actual3.getState());


        eventService.delete(actual.getId());
        eventService.delete(actual2.getId());
        eventService.delete(actual3.getId());
    }

    @Test
    public void readByIdResponseTest() {
        EventRequest eventRequest = new EventRequest();

        eventRequest.setTitle("Football school 3");
        eventRequest.setMaxParticipants(12);
        eventRequest.setStartAt(LocalDateTime.of(2024, Month.APRIL, 1, 10, 10, 30));
        eventRequest.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest.setLocation("37.0902 95.7129");
        eventRequest.setOwnerId(10);


        EventResponse expected = eventService.create(eventRequest);
        EventResponse actual = eventService.readById(expected.getId());
        actual.setCreatedAt(expected.getCreatedAt());

        System.out.println("\n\n\n");
        System.out.println(actual);
        System.out.println(eventService.getAll());
        System.out.println("\n\n");



        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected, actual);


        eventService.delete(actual.getId());
    }

    @Test
    public void checkUpdateEvent() {
        EventRequest eventRequest = new EventRequest();

        eventRequest.setTitle("Football school ");
        eventRequest.setMaxParticipants(12);
        eventRequest.setStartAt(LocalDateTime.of(2024, Month.APRIL, 1, 10, 10, 30));
        eventRequest.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest.setLocation("37.0902 95.7129");
        eventRequest.setOwnerId(10);

        EventRequest eventRequest2 = new EventRequest();

        eventRequest2.setTitle("Football school ");
        eventRequest2.setMaxParticipants(10);
        eventRequest2.setStartAt(LocalDateTime.of(2023, Month.APRIL, 1, 10, 10, 30));
        eventRequest2.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest2.setDescription("Top football near school 34 NEWWW");
        eventRequest2.setLocation("37.0902 95.7129");
        eventRequest2.setOwnerId(10);


        EventResponse actual = eventService.create(eventRequest);
        long idToCheck = actual.getId();

        EventResponse updatedEvent = eventService.update(eventRequest2);


        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(updatedEvent);
        Assertions.assertEquals(eventService.getAll().size(), 4);
        Assertions.assertEquals("Top football near school 34 NEWWW", updatedEvent.getDescription());


        eventService.delete(updatedEvent.getId());
    }

    @Test
    public void checkInvalidDateUpdateEvent() {
        EventRequest eventRequest = new EventRequest();

        eventRequest.setTitle("Football school 345");
        eventRequest.setMaxParticipants(12);
        eventRequest.setStartAt(LocalDateTime.of(2024, Month.APRIL, 1, 10, 10, 30));
        eventRequest.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest.setLocation("37.0902 95.7129");
        eventRequest.setOwnerId(10);

        EventRequest eventRequest2 = new EventRequest();

        eventRequest2.setTitle("Football school 345");
        eventRequest2.setMaxParticipants(10);
        eventRequest2.setStartAt(LocalDateTime.of(2026, Month.APRIL, 1, 10, 10, 30));
        eventRequest2.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest2.setDescription("Top football near school 34 NEWWW");
        eventRequest2.setLocation("37.0902 95.7129");
        eventRequest2.setOwnerId(10);


        EventResponse actual = eventService.create(eventRequest);
        long idToCheck = actual.getId();




        Assertions.assertThrows(NotValidDateException.class,
                () -> {
                    EventResponse updatedEvent = eventService.update(eventRequest2);
                });

        eventService.delete(actual.getId());
    }

    @Test
    public void checkStatesUpdateEvent() {
        EventRequest eventRequest = new EventRequest();

        eventRequest.setTitle("Football school 344");
        eventRequest.setMaxParticipants(12);
        eventRequest.setStartAt(LocalDateTime.of(2023, Month.APRIL, 1, 10, 10, 30));
        eventRequest.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest.setLocation("37.0902 95.7129");
        eventRequest.setOwnerId(10);

        EventRequest eventRequest2 = new EventRequest();

        eventRequest2.setTitle("Football2 school 344");
        eventRequest2.setMaxParticipants(12);
        eventRequest2.setStartAt(LocalDateTime.of(2023, Month.APRIL, 1, 10, 10, 30));
        eventRequest2.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest2.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest2.setLocation("37.0902 95.7129");
        eventRequest2.setOwnerId(10);

        EventRequest eventRequest3 = new EventRequest();

        eventRequest3.setTitle("Football3 school 344");
        eventRequest3.setMaxParticipants(12);
        eventRequest3.setStartAt(LocalDateTime.of(2023, Month.APRIL, 1, 10, 10, 30));
        eventRequest3.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest3.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest3.setLocation("37.0902 95.7129");
        eventRequest3.setOwnerId(10);

        EventRequest eventRequest11 = new EventRequest();

        eventRequest11.setTitle("Football school 344");
        eventRequest11.setMaxParticipants(12);
        eventRequest11.setStartAt(LocalDateTime.of(2023, Month.APRIL, 1, 10, 10, 30));
        eventRequest11.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest11.setDescription("Top football near school 34 come Newww");
        eventRequest11.setLocation("37.0902 95.7129");
        eventRequest11.setOwnerId(10);

        EventRequest eventRequest22 = new EventRequest();

        eventRequest22.setTitle("Football2 school 344");
        eventRequest22.setMaxParticipants(12);
        eventRequest22.setStartAt(LocalDateTime.of(2024, Month.APRIL, 1, 10, 10, 30));
        eventRequest22.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest22.setDescription("Top football near school 34 NEW");
        eventRequest22.setLocation("37.0902 95.7129");
        eventRequest22.setOwnerId(10);

        EventRequest eventRequest33 = new EventRequest();

        eventRequest33.setTitle("Football3 school 344");
        eventRequest33.setMaxParticipants(12);
        eventRequest33.setStartAt(LocalDateTime.of(2023, Month.APRIL, 1, 10, 10, 30));
        eventRequest33.setExpireAt(LocalDateTime.of(2023, Month.APRIL, 1, 12, 10, 30));
        eventRequest33.setDescription("Top football near school 34 NEW 333");
        eventRequest33.setLocation("37.0902 95.7129");
        eventRequest33.setOwnerId(10);



        eventService.create(eventRequest);

        eventService.create(eventRequest2);

        eventService.create(eventRequest3);


        EventResponse updated11 = eventService.update(eventRequest11);
        EventResponse updated22 = eventService.update(eventRequest22);
        EventResponse updated33 = eventService.update(eventRequest33);

        Assertions.assertEquals(State.LIVE, updated11.getState());
        Assertions.assertEquals(State.UPCOMING, updated22.getState());
        Assertions.assertEquals(State.PAST, updated33.getState());

        eventService.delete(updated11.getId());
        eventService.delete(updated22.getId());
        eventService.delete(updated33.getId());
    }

    @Test
    public void checkDeleteEvent() {
        EventRequest eventRequest = new EventRequest();

        eventRequest.setTitle("Football centre");
        eventRequest.setMaxParticipants(12);
        eventRequest.setStartAt(LocalDateTime.of(2024, Month.APRIL, 1, 10, 10, 30));
        eventRequest.setExpireAt(LocalDateTime.of(2024, Month.APRIL, 1, 12, 10, 30));
        eventRequest.setDescription("Top football near school 34 come oooon, intermediate lvl");
        eventRequest.setLocation("37.0902 95.7129");
        eventRequest.setOwnerId(10);


        EventResponse actual = eventService.create(eventRequest);

        eventService.delete(actual.getId());
        System.out.println("\n\n"+ eventService.getAll()+"\n\n");

        Assertions.assertEquals(3, eventService.getAll().size());
    }

    @Test
    public void checkDeleteInvalidEvent() {


        Assertions.assertThrows(EntityNotFoundException.class,
                () -> {eventService.delete(76); });
    }
}

