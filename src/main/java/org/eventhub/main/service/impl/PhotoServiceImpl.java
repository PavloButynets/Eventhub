package org.eventhub.main.service.impl;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.specialized.BlockBlobClient;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.eventhub.main.config.JwtService;
import org.eventhub.main.dto.PhotoRequest;
import org.eventhub.main.dto.PhotoResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.mapper.PhotoMapper;
import org.eventhub.main.model.Photo;
import org.eventhub.main.repository.PhotoRepository;
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.PhotoService;
import org.eventhub.main.service.UserService;
import org.eventhub.main.utility.BlobContainerClientSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final PhotoMapper photoMapper;
    private final EventService eventService;
    private final UserService userService;
    private final BlobContainerClient blobContainerClientEvent;
    private final BlobContainerClient blobContainerClientUser;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMapper photoMapper, EventService eventService, UserService userService){
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
        this.eventService = eventService;
        this.userService = userService;

        this.blobContainerClientEvent = BlobContainerClientSingleton.getInstance().getBlobContainerClientEvent();
        this.blobContainerClientUser = BlobContainerClientSingleton.getInstance().getBlobContainerClientUser();
    }
    @Override
    public PhotoResponse create(PhotoRequest photoRequest) {
        if(photoRequest != null){
            Photo photo = photoMapper.requestToEntity(photoRequest, new Photo());
            return photoMapper.entityToResponse(photoRepository.save(photo));
        }
        throw new NullDtoReferenceException("Created photo Request can't be null");
    }
    @Override
    public PhotoResponse readById(UUID id) {
        Photo photo= photoRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Photo with" + id + " id is not found"));
        return photoMapper.entityToResponse(photo);
    }
    @Override
    public Photo readByIdEntity(UUID id){
        return photoRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Photo with " + id + " id is not found"));
    }
    @Override
    public PhotoResponse update(PhotoRequest photoRequest, UUID id) {
        if (photoRequest != null) {
            Photo existingPhoto = readByIdEntity(id);
            Photo photo = photoMapper.requestToEntity(photoRequest, existingPhoto);
            return photoMapper.entityToResponse(photoRepository.save(photo));
        }
        throw new NullDtoReferenceException("Updated photo Request cannot be 'null'");
    }
    @Override
    public void deleteEventImage(UUID eventId, UUID imageId, String token) {
        eventService.validateEventOwner(token, eventId);

        String blobName = this.readById(imageId).getPhotoName();
        BlockBlobClient blockBlobClient = this.blobContainerClientEvent.getBlobClient(blobName).getBlockBlobClient();
        blockBlobClient.delete();
        eventService.deleteImage(eventId, this.readByIdEntity(imageId));
        photoRepository.delete(readByIdEntity(imageId));
    }

    @Override
    public void deleteProfileImage(UUID ownerId, UUID imageId) {

        String blobName = this.readById(imageId).getPhotoName();
        BlockBlobClient blockBlobClient = this.blobContainerClientUser.getBlobClient(blobName).getBlockBlobClient();
        blockBlobClient.delete();
        userService.deleteImage(ownerId, this.readByIdEntity(imageId));
        photoRepository.delete(readByIdEntity(imageId));
    }

    @Override
    public List<PhotoResponse> getAll() {
        return photoRepository.findAll()
                .stream()
                .map(photoMapper::entityToResponse)
                .collect(Collectors.toList());
    }
    @Override
    public List<PhotoResponse> uploadEventPhotos(UUID eventId, List<MultipartFile> files, String token){
        eventService.validateEventOwner(token, eventId);

        List<PhotoResponse> responses = new ArrayList<>();
            for (MultipartFile file : files) {
                try (ByteArrayInputStream dataStream = new ByteArrayInputStream(file.getBytes())) {
                    Photo photo = new Photo();
                    photo.setId(UUID.randomUUID());

                    String fileExtension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
                    BlockBlobClient blockBlobClient = this.blobContainerClientEvent.getBlobClient("event" + photo.getId().toString() + "." + fileExtension).getBlockBlobClient();
                    blockBlobClient.upload(dataStream, file.getSize());

                    photo.setPhotoName(blockBlobClient.getBlobName());
                    photo.setPhotoUrl(blockBlobClient.getBlobUrl());

                    eventService.addImage(eventId, photo);

                    responses.add(photoMapper.entityToResponse(photoRepository.save(photo)));
                }
                catch (IOException ex){
                    throw new ResponseStatusException("Failed to download images");
                }
            }
        return responses;
    }

    @Override
    public List<PhotoResponse> uploadProfilePhotos(UUID userId, List<MultipartFile> files) {
        List<PhotoResponse> responses = new ArrayList<>();
        for(MultipartFile file: files) {
            try (ByteArrayInputStream dataStream = new ByteArrayInputStream(file.getBytes())) {
                Photo photo = new Photo();
                photo.setId(UUID.randomUUID());

                String fileExtension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
                BlockBlobClient blockBlobClient = this.blobContainerClientUser.getBlobClient("user" + photo.getId().toString() + "." + fileExtension).getBlockBlobClient();
                blockBlobClient.upload(dataStream, file.getSize());

                photo.setPhotoName(blockBlobClient.getBlobName());
                photo.setPhotoUrl(blockBlobClient.getBlobUrl());

                userService.addImage(userId, photo);

                responses.add(photoMapper.entityToResponse(photoRepository.save(photo)));
            } catch (IOException ex) {
                throw new ResponseStatusException("Failed to download images");
            }
        }
        return responses;
    }


}
