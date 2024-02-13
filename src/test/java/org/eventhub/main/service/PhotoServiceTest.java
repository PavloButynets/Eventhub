package org.eventhub.main.service;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.mapper.EventPhotoMapper;
import org.eventhub.main.mapper.UserMapper;
import org.eventhub.main.model.Category;
import org.eventhub.main.model.EventPhoto;
import org.eventhub.main.model.Gender;
import org.eventhub.main.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class PhotoServiceTest {

    public final PhotoService photoService;
    @Autowired
    public PhotoServiceTest(PhotoService photoService) {
        this.photoService = photoService;
    }

    @Test
    public void createValidPhotoTest() {
        EventPhotoRequest request = new EventPhotoRequest("New Photo #1", 30L);
        EventPhotoResponse response = photoService.create(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(request.getPhotoUrl(),response.getPhotoUrl());
        Assertions.assertEquals(4, photoService.getAll().size());
        Assertions.assertNotNull(response.getId());

        photoService.delete(response.getId());
    }

    @Test
    public void createInvalidPhotoTest(){
        Assertions.assertThrows(NullDtoReferenceException.class, () -> photoService.create(null));
    }
    @Test
    public void readByIdValidPhotoResponseTest(){
        EventPhotoResponse response = photoService.readById(10);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Photo 1", response.getPhotoUrl());
        Assertions.assertEquals(3, photoService.getAll().size());
    }
    @Test
    public void readByIdInvalidPhotoResponseTest(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> photoService.readById(100));
    }
    @Test
    public void readByIdEntityValidPhotoTest(){
        EventPhoto photo = photoService.readByIdEntity(10);
        Assertions.assertNotNull(photo);
        Assertions.assertEquals("Photo 1", photo.getPhotoUrl());
        Assertions.assertEquals(3, photoService.getAll().size());
    }
    @Test
    public void readByIdEntityInvalidCategoryTest(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> photoService.readByIdEntity(100));
    }

    @Test
    public void updateValidPhotoTest(){
        EventPhotoResponse response = photoService.create(new EventPhotoRequest("Created Photo#2", 30L));
        EventPhotoRequest request = new EventPhotoRequest("Updated photo", 30L);
        EventPhotoResponse updatedResponse = photoService.update(request, response.getId());

        Assertions.assertNotNull(updatedResponse);
        Assertions.assertEquals(request.getPhotoUrl(), updatedResponse.getPhotoUrl());
        Assertions.assertEquals(4, photoService.getAll().size());

        photoService.delete(response.getId());
    }
    @Test
    void updateInvalidPhotoTest1(){
        Assertions.assertThrows(NullDtoReferenceException.class, () -> photoService.update(null, 10));
    }
    @Test
    void updateInvalidPhotoTest2(){
        Assertions.assertThrows(EntityNotFoundException.class, () -> photoService.update(new EventPhotoRequest("Created Photo#2", 30L), 100));
    }
    @Test
    void deleteValidParticipantTest(){
        EventPhotoRequest request = new EventPhotoRequest("New Photo #3",30L);
        EventPhotoResponse response = photoService.create(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(4, photoService.getAll().size());

        photoService.delete(response.getId());

        Assertions.assertEquals(3, photoService.getAll().size());
    }
    @Test
    void getAllValidCategoryTest(){
        EventPhotoResponse response = photoService.create(new EventPhotoRequest("New Photo #4",30L));
        List<EventPhotoResponse> all = photoService.getAll();

        Assertions.assertEquals(4, all.size());
        Assertions.assertEquals("Photo 1", all.get(0).getPhotoUrl());
        Assertions.assertEquals("Photo 2", all.get(1).getPhotoUrl());
        Assertions.assertEquals("Photo 3", all.get(2).getPhotoUrl());
        Assertions.assertEquals("New Photo #4", all.get(3).getPhotoUrl());


        photoService.delete(response.getId());
    }
}
