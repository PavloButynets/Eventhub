package org.eventhub.main.service;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Participant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class ParticipantServiceTest {
    private final ParticipantService participantService;

    @Autowired
    public ParticipantServiceTest(ParticipantService participantService){
        this.participantService = participantService;
    }

    @Test
    public void createValidParticipantTest(){
        ParticipantRequest request = new ParticipantRequest(30L, 10L, false);
        ParticipantResponse response = participantService.create(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(6, participantService.getAll().size());
        Assertions.assertEquals(request.getEventId(), response.getEventId());
        Assertions.assertEquals(request.getUserId(), response.getUserId());
        Assertions.assertNotNull(response.getId());

        participantService.delete(response.getId());
    }

    @Test
    public void createInvalidParticipantTest1(){
        ParticipantRequest request = new ParticipantRequest(30L, 100L, false);
        Assertions.assertThrows(NullEntityReferenceException.class, () -> participantService.create(request));
    }

    @Test
    public void createInvalidParticipantTest2(){
        ParticipantRequest request = new ParticipantRequest(100L, 30L, false);
        Assertions.assertThrows(NullEntityReferenceException.class, () -> participantService.create(request));
    }

    @Test
    public void createInvalidParticipantTest3(){
        Assertions.assertThrows(NullDtoReferenceException.class, () -> participantService.create(null));
    }

    @Test
    public void readByIdValidParticipantTest(){
        ParticipantResponse actual = participantService.readById(10);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime localDateTime = LocalDateTime.parse("2020-11-16 14:00:04.810221", formatter);

        ParticipantResponse expected = new ParticipantResponse(10L, localDateTime, false, 30L, 11L);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(5, participantService.getAll().size());
    }

    @Test
    public void readByIdInvalidParticipantTest(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> participantService.readById(100));
    }

    @Test
    public void readByIdEntityValidParticipantTest(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime localDateTime = LocalDateTime.parse("2020-11-16 14:00:04.810221", formatter);

        Participant actual = participantService.readByIdEntity(10);

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(10L, actual.getId());
        Assertions.assertFalse(actual.isApproved());
        Assertions.assertEquals(30L, actual.getEvent().getId());
        Assertions.assertEquals(11L, actual.getUser().getId());
        Assertions.assertEquals(localDateTime, actual.getCreatedAt());
        Assertions.assertEquals(5, participantService.getAll().size());
    }

    @Test
    public void updateValidParticipantTest(){
        ParticipantResponse response = participantService.create(new ParticipantRequest(30L, 10L, false));
        ParticipantRequest request = new ParticipantRequest(30L, 11L, true);
        ParticipantResponse actual = participantService.update(request, response.getId());

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(participantService.readById(response.getId()), actual);
        Assertions.assertEquals(6, participantService.getAll().size());

        participantService.delete(response.getId());
    }

    @Test
    public void updateInvalidParticipantTest1(){
        Assertions.assertThrows(NullDtoReferenceException.class, () -> participantService.update(null, 10));
    }

    @Test
    void updateInvalidParticipantTest2(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> participantService.update(new ParticipantRequest(10L, 11L, true), 100));
    }

    @Test
    void updateInvalidParticipantTest3(){
        Assertions.assertThrows(NullEntityReferenceException.class, () -> participantService.update(new ParticipantRequest(100L, 11L, true), 10));
    }

    @Test
    void updateInvalidParticipantTest4(){
        Assertions.assertThrows(NullEntityReferenceException.class, () -> participantService.update(new ParticipantRequest(10L, 110L, true), 10));
    }

    @Test
    void deleteValidParticipantTest(){
        ParticipantRequest request = new ParticipantRequest(30L, 10L, false);
        ParticipantResponse response = participantService.create(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(6, participantService.getAll().size());

        participantService.delete(response.getId());

        Assertions.assertEquals(5, participantService.getAll().size());
    }

    @Test
    void deleteInvalidParticipantTest(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> participantService.delete(100L));
    }

    @Test
    void getAllValidParticipantTest(){
        ParticipantRequest request = new ParticipantRequest(30L, 10L, false);
        ParticipantResponse response = participantService.create(request);

        Assertions.assertEquals(6, participantService.getAll().size());
        participantService.delete(response.getId());
    }
}
