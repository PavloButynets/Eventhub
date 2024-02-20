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

@RestController
@Slf4j
@RequestMapping("/users/{user_id}/events/{event_id}/photos")
public class PhotoController {
    private static final String connectionString = "DefaultEndpointsProtocol=https;AccountName=eventhubproject;AccountKey=KDM2AysSXKu3dXoifZKlsp0/D3Fz4APx6ySAGEJeFtLpQ/AZG3UhG0wUyIscSPXXVsWjFVYYWbiP+ASt1bCrLw==;EndpointSuffix=core.windows.net";
    private static final String containerName = "images";
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
    public ResponseEntity<OperationResponse> delete(@PathVariable("photo_id") long photoId) throws UnsupportedEncodingException {
        String blobUrl = photoService.readById(photoId).getPhotoUrl();
        BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();
        BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(blobUrl).getBlockBlobClient();

        blockBlobClient.delete();

        log.info("**/deleted user(id) = " + photoId);
        photoService.delete(photoId);
        return new ResponseEntity<>(new OperationResponse("EventPhoto " + blobUrl + " deleted successfully"), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImages(@PathVariable(name = "event_id") Long eventId,
                                               @RequestPart("files") List<MultipartFile> files) {
        try {
            BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
                    .connectionString(connectionString)
                    .containerName(containerName)
                    .buildClient();

            for (MultipartFile file : files) {
                try (ByteArrayInputStream dataStream = new ByteArrayInputStream(file.getBytes())) {
                    BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(file.getOriginalFilename()).getBlockBlobClient();
                    blockBlobClient.upload(dataStream, file.getSize());

                    String imageUrl = blockBlobClient.getBlobName();
                    photoService.create(new EventPhotoRequest(imageUrl, eventId));
                }
            }
            return ResponseEntity.ok("Images uploaded successfully.");
        } catch (IOException e) {
            log.error("Error uploading images.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading images.");
        }
    }

}

