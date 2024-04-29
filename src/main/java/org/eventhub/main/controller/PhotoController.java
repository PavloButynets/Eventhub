package org.eventhub.main.controller;

import lombok.extern.slf4j.Slf4j;
import org.eventhub.main.config.JwtService;
import org.eventhub.main.dto.*;
import org.eventhub.main.exception.AccessIsDeniedException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.model.Event;
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
@RequestMapping
public class PhotoController {
    private final PhotoService photoService;
    private final JwtService jwtService;
    @Autowired
    public PhotoController(PhotoService photoService, JwtService jwtService) {

        this.photoService = photoService;
        this.jwtService = jwtService;
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
    public ResponseEntity<OperationResponse> deleteEventImage(@RequestHeader (name="Authorization") String token, @PathVariable("photo_id") UUID photoId,
                                                              @PathVariable("event_id") UUID evenId) {
        log.info("**/deleted user(id) = " + photoId.toString());
        photoService.deleteEventImage(evenId, photoId, token);
        return new ResponseEntity<>(new OperationResponse("Photo id:" + photoId + " deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/events/{event_id}/photos/upload")
    public ResponseEntity<List<PhotoResponse>> uploadEventImages(@RequestHeader (name="Authorization") String token, @PathVariable(name = "event_id") UUID eventId,
                                                            @RequestPart("files") List<MultipartFile> files){
        log.info("Uploading photos");
        return new ResponseEntity<>(this.photoService.uploadEventPhotos(eventId, files, token), HttpStatus.CREATED);
    }

    @DeleteMapping("/users/photos/{photo_id}")
    public ResponseEntity<OperationResponse> deleteProfileImage(@RequestHeader("Authorization") String token,
                                                                @PathVariable("photo_id") UUID photoId) {
        log.info("**/deleted user(id) = " + photoId.toString());
        photoService.deleteProfileImage(jwtService.getId(token), photoId);
        return new ResponseEntity<>(new OperationResponse("Photo id:" + photoId + " deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/users/photos/upload")
    public ResponseEntity<List<PhotoResponse>> uploadProfileImage(@RequestHeader("Authorization") String token,
                                                                 @RequestPart("files") List<MultipartFile> files){
        log.info("Uploading photos");
        return new ResponseEntity<>(this.photoService.uploadProfilePhotos(jwtService.getId(token), files), HttpStatus.CREATED);
    }



}

