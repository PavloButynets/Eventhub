package org.eventhub.main.service.impl;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.eventhub.main.dto.EventPhotoRequest;
import org.eventhub.main.dto.EventPhotoResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.mapper.EventPhotoMapper;
import org.eventhub.main.model.EventPhoto;
import org.eventhub.main.repository.PhotoRepository;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.PhotoService;
import org.eventhub.main.utility.BlobContainerClientSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final EventPhotoMapper eventPhotoMapper;
    private final BlobContainerClient blobContainerClient;
    private final EventService eventService;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, EventPhotoMapper eventPhotoMapper, EventService eventService){
        this.photoRepository = photoRepository;
        this.eventPhotoMapper = eventPhotoMapper;
        this.eventService = eventService;
        this.blobContainerClient = BlobContainerClientSingleton.getInstance().getBlobContainerClient();
    }
    @Override
    public EventPhotoResponse create(EventPhotoRequest eventPhotoRequest) {
        if(eventPhotoRequest != null){
            EventPhoto photo = eventPhotoMapper.requestToEntity(eventPhotoRequest, new EventPhoto());
            return eventPhotoMapper.entityToResponse(photoRepository.save(photo));
        }
        throw new NullDtoReferenceException("Created photo Request can't be null");
    }
    @Override
    public EventPhotoResponse readById(UUID id) {
        EventPhoto photo= photoRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Photo with" + id + " id is not found"));
        return eventPhotoMapper.entityToResponse(photo);
    }
    @Override
    public EventPhoto readByIdEntity(UUID id){
        return photoRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Photo with " + id + " id is not found"));
    }
    @Override
    public EventPhotoResponse update(EventPhotoRequest photoRequest, UUID id) {
        if (photoRequest != null) {
            EventPhoto existingPhoto = readByIdEntity(id);
            EventPhoto photo = eventPhotoMapper.requestToEntity(photoRequest, existingPhoto);
            return eventPhotoMapper.entityToResponse(photoRepository.save(photo));
        }
        throw new NullDtoReferenceException("Updated photo Request cannot be 'null'");
    }
    @Override
    public void delete(UUID id) {
        String blobName = this.readById(id).getPhotoName();
        BlockBlobClient blockBlobClient = this.blobContainerClient.getBlobClient(blobName).getBlockBlobClient();
        blockBlobClient.delete();
        photoRepository.delete(readByIdEntity(id));
    }

    @Override
    public List<EventPhotoResponse> getAll() {
        return photoRepository.findAll()
                .stream()
                .map(eventPhotoMapper::entityToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<EventPhotoResponse> uploadPhotos(UUID eventId, List<MultipartFile> files){
        List<EventPhotoResponse> responses = new ArrayList<>();
            for (MultipartFile file : files) {
                try (ByteArrayInputStream dataStream = new ByteArrayInputStream(file.getBytes())) {
                    EventPhoto photo = new EventPhoto();
                    photo.setId(UUID.randomUUID());

                    String fileExtension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

                    BlockBlobClient blockBlobClient = this.blobContainerClient.getBlobClient(photo.getId().toString() + "." + fileExtension).getBlockBlobClient();
                    blockBlobClient.upload(dataStream, file.getSize());

                    photo.setPhotoName(blockBlobClient.getBlobName());
                    photo.setPhotoUrl(blockBlobClient.getBlobUrl());
                    photo.setEvent(eventService.readByIdEntity(eventId));

                    responses.add(eventPhotoMapper.entityToResponse(photoRepository.save(photo)));
                }
                catch (IOException ex){
                    throw new ResponseStatusException("Failed to download images");
                }
            }
        return responses;
    }

}
