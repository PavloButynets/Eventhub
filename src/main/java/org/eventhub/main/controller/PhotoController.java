package org.eventhub.main.controller;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.service.PhotoService;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/users/{user_id}/events/{event_id}/photos")
public class PhotoController {
    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping
    List<EventPhotoResponse> getAll() {
        return photoService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventPhotoResponse> create(@Validated @RequestBody EventPhotoRequest eventPhotoRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        EventPhotoResponse eventPhotoResponse = photoService.create(eventPhotoRequest);
        log.info("**/created eventPhoto(id) = " + eventPhotoResponse.getId());

        return new ResponseEntity<>(eventPhotoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{photo_id}")
    public ResponseEntity<EventPhotoResponse> update(@PathVariable("photo_id") UUID photoId,
                                               @Validated @RequestBody EventPhotoRequest eventPhotoRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        EventPhotoResponse eventPhotoResponse = photoService.update(eventPhotoRequest, photoId);
        log.info("**/updated eventPhoto(id) = " + photoId);

        return new ResponseEntity<>(eventPhotoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{photo_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("photo_id") UUID photoId) {
        log.info("**/deleted user(id) = " + photoId.toString());
        photoService.delete(photoId);
        return new ResponseEntity<>(new OperationResponse("EventPhoto id:" + photoId + " deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<EventPhotoResponse>> uploadImages(@PathVariable(name = "event_id") UUID eventId,
                                               @RequestPart("files") List<MultipartFile> files){
        log.info("Uploading photos");
        return new ResponseEntity<>(this.photoService.uploadPhotos(eventId, files), HttpStatus.CREATED);
    }

}

