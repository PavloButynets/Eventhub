package org.eventhub.main.service.impl;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.EventPhotoRequest;
import org.eventhub.main.dto.EventPhotoResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.exception.ResponseStatusException;
import org.eventhub.main.mapper.EventPhotoMapper;
import org.eventhub.main.model.EventPhoto;
import org.eventhub.main.repository.PhotoRepository;
import org.eventhub.main.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final EventPhotoMapper eventPhotoMapper;
    private static final String connectionString = "DefaultEndpointsProtocol=https;AccountName=eventhubproject;AccountKey=KDM2AysSXKu3dXoifZKlsp0/D3Fz4APx6ySAGEJeFtLpQ/AZG3UhG0wUyIscSPXXVsWjFVYYWbiP+ASt1bCrLw==;EndpointSuffix=core.windows.net";
    private static final String containerName = "images";
    private final BlobContainerClient blobContainerClient;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, EventPhotoMapper eventPhotoMapper){
        this.photoRepository = photoRepository;
        this.eventPhotoMapper = eventPhotoMapper;

        this.blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();
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
    public EventPhotoResponse readById(long id) {
        EventPhoto photo= photoRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Photo with" + id + " id is not found"));
        return eventPhotoMapper.entityToResponse(photo);
    }
    @Override
    public EventPhoto readByIdEntity(long id){
        return photoRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Photo with " + id + " id is not found"));
    }
    @Override
    public EventPhotoResponse update(EventPhotoRequest photoRequest, long id) {
        if (photoRequest != null) {
            EventPhoto existingPhoto = readByIdEntity(id);
            EventPhoto photo = eventPhotoMapper.requestToEntity(photoRequest, existingPhoto);
            return eventPhotoMapper.entityToResponse(photoRepository.save(photo));
        }
        throw new NullDtoReferenceException("Updated photo Request cannot be 'null'");
    }
    @Override
    public void delete(long id) {
        String blobUrl = this.readById(id).getPhotoUrl();
        BlockBlobClient blockBlobClient = this.blobContainerClient.getBlobClient(blobUrl).getBlockBlobClient();
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
    public List<EventPhotoResponse> uploadPhotos(long eventId, List<MultipartFile> files){
        List<EventPhotoResponse> responses = new ArrayList<>();
            for (MultipartFile file : files) {
                try (ByteArrayInputStream dataStream = new ByteArrayInputStream(file.getBytes())) {
                    BlockBlobClient blockBlobClient = this.blobContainerClient.getBlobClient(file.getOriginalFilename()).getBlockBlobClient();
                    blockBlobClient.upload(dataStream, file.getSize());

                    String imageUrl = blockBlobClient.getBlobName();
                    responses.add(this.create(new EventPhotoRequest(imageUrl, eventId)));
                }
                catch (IOException ex){
                    throw new ResponseStatusException("Failed to download images");
                }
            }
        return responses;
    }

}
