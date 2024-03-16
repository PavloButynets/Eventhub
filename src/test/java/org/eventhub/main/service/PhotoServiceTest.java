package org.eventhub.main.service;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.model.Photo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        PhotoRequest request = new PhotoRequest("New Photo #1", 30L);
        PhotoResponse response = photoService.create(request);

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
        PhotoResponse response = photoService.readById(10);
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
        Photo photo = photoService.readByIdEntity(10);
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
        PhotoResponse response = photoService.create(new PhotoRequest("Created Photo#2", 30L));
        PhotoRequest request = new PhotoRequest("Updated photo", 30L);
        PhotoResponse updatedResponse = photoService.update(request, response.getId());

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
        Assertions.assertThrows(EntityNotFoundException.class, () -> photoService.update(new PhotoRequest("Created Photo#2", 30L), 100));
    }
    @Test
    void deleteValidParticipantTest(){
        PhotoRequest request = new PhotoRequest("New Photo #3",30L);
        PhotoResponse response = photoService.create(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(4, photoService.getAll().size());

        photoService.delete(response.getId());

        Assertions.assertEquals(3, photoService.getAll().size());
    }
    @Test
    void getAllValidCategoryTest(){
        PhotoResponse response = photoService.create(new PhotoRequest("New Photo #4",30L));
        List<PhotoResponse> all = photoService.getAll();

        Assertions.assertEquals(4, all.size());
        Assertions.assertEquals("Photo 1", all.get(0).getPhotoUrl());
        Assertions.assertEquals("Photo 2", all.get(1).getPhotoUrl());
        Assertions.assertEquals("Photo 3", all.get(2).getPhotoUrl());
        Assertions.assertEquals("New Photo #4", all.get(3).getPhotoUrl());


        photoService.delete(response.getId());
    }
}
