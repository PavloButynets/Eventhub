package org.eventhub.main.controller;

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

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users/{user_id}/events/{event_id}/photos")
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
    public ResponseEntity<EventPhotoResponse> update(@PathVariable("photo_id") long photoId,
                                               @Validated @RequestBody EventPhotoRequest eventPhotoRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException("Invalid Input");
        }

        EventPhotoResponse eventPhotoResponse = photoService.update(eventPhotoRequest, photoId);
        log.info("**/updated eventPhoto(id) = " + photoId);

        return new ResponseEntity<>(eventPhotoResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{photo_id}")
    public ResponseEntity<OperationResponse> delete(@PathVariable("photo_id") long photoId) {
        log.info("**/deleted user(id) = " + photoId);
        photoService.delete(photoId);
        return new ResponseEntity<>(new OperationResponse("EventPhoto deleted successfully"), HttpStatus.OK);
    }
}

