package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/users/{user_id}")
public class PhotoController {
    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/photos")
    List<PhotoResponse> getAll() {
        return photoService.getAll();
    }

    @PostMapping("/photos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PhotoResponse> create(@Validated @RequestBody PhotoRequest photoRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        PhotoResponse photoResponse = photoService.create(photoRequest);
        log.info("**/created eventPhoto(id) = " + photoResponse.getId());

        return new ResponseEntity<>(photoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{photo_id}")
    public ResponseEntity<PhotoResponse> update(@PathVariable("photo_id") UUID photoId,
                                                @Validated @RequestBody PhotoRequest photoRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        PhotoResponse photoResponse = photoService.update(photoRequest, photoId);
        log.info("**/updated eventPhoto(id) = " + photoId);

        return new ResponseEntity<>(photoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/events/{event_id}/photos/{photo_id}")
    public ResponseEntity<OperationResponse> deleteEventImage(@PathVariable("photo_id") UUID photoId,
                                                              @PathVariable("event_id") UUID evenId) {
        log.info("**/deleted user(id) = " + photoId.toString());
        photoService.deleteEventImage(evenId,photoId);
        return new ResponseEntity<>(new OperationResponse("Photo id:" + photoId + " deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/events/{event_id}/photos/upload")
    public ResponseEntity<List<PhotoResponse>> uploadEventImages(@PathVariable(name = "event_id") UUID eventId,
                                                            @RequestPart("files") List<MultipartFile> files){
        log.info("Uploading photos");
        return new ResponseEntity<>(this.photoService.uploadEventPhotos(eventId, files), HttpStatus.CREATED);
    }

    @DeleteMapping("/photos/{photo_id}")
    public ResponseEntity<OperationResponse> deleteProfileImage(@PathVariable("photo_id") UUID photoId,
                                                                @PathVariable("user_id") UUID userId) {
        log.info("**/deleted user(id) = " + photoId.toString());
        photoService.deleteProfileImage(userId, photoId);
        return new ResponseEntity<>(new OperationResponse("Photo id:" + photoId + " deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/photos/upload")
    public ResponseEntity<PhotoResponse> uploadProfileImage(@PathVariable(name = "user_id") UUID userId,
                                                                 @RequestPart("file") MultipartFile file){
        log.info("Uploading photos");
        return new ResponseEntity<>(this.photoService.uploadProfilePhotos(userId, file), HttpStatus.CREATED);
    }

}

