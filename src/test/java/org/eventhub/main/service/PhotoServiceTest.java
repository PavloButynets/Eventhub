package org.eventhub.main.service;

import org.eventhub.main.dto.EventPhotoRequest;
import org.eventhub.main.dto.EventPhotoResponse;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.mapper.EventPhotoMapper;
import org.eventhub.main.mapper.UserMapper;
import org.eventhub.main.model.EventPhoto;
import org.eventhub.main.model.Gender;
import org.eventhub.main.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class PhotoServiceTest {

    public final PhotoService photoService;
    public final EventPhotoMapper eventPhotoMapper;
    @Autowired
    public PhotoServiceTest(UserMapper userMapper, PhotoService photoService, EventPhotoMapper eventPhotoMapper) {
        this.photoService = photoService;
        this.eventPhotoMapper = eventPhotoMapper;
    }

    @Test
    public void createPhotoTest() {
        EventPhotoRequest photoRequest = new EventPhotoRequest();

        photoRequest.setPhotoUrl("photo.url");
        photoRequest.setEventId(10L);

        EventPhotoResponse actual = photoService.create(photoRequest);

        EventPhoto photo = eventPhotoMapper.requestToEntity(photoRequest, new EventPhoto());
        EventPhotoResponse expected = eventPhotoMapper.entityToResponse(photo);

        Assertions.assertEquals(expected, actual);
    }

//    @Test
//    public void readByIdPhotoTest() {
//        long id = 11L;
//        EventPhoto photo = new EventPhoto();
//        photo.setId(id);
//        photo.setPhotoUrl("Photo 2");
//
//        EventPhotoResponse actual = photoService.readById(11L);
//        EventPhotoResponse expected = EventPhotoResponse.builder()
//                .id(photo.getId())
//                .photoUrl(photo.getPhotoUrl())
//                .eventId(photo.getEvent().getId())
//                .build();
//
//        Assertions.assertEquals(expected, actual);
//    }

    @Test
    public void updatePhotoTest() {
        EventPhotoRequest photoRequest = new EventPhotoRequest();

        photoRequest.setPhotoUrl("photo.url");
        photoRequest.setEventId(11L);

        EventPhotoResponse actual = photoService.create(photoRequest);
        EventPhotoResponse updated = photoService.update(photoRequest, actual.getId());

        Assertions.assertEquals(updated, actual);
    }

    @Test
    public void deletePhotoTest() {
        photoService.delete(1);
        Assertions.assertEquals(3, photoService.getAll().size());
    }

}
