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
import org.eventhub.main.service.EventService;
import org.eventhub.main.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final BlobContainerClient blobContainerClient;
    private final EventService eventService;

    private EventPhoto createModel(EventPhotoRequest eventPhotoRequest){
        return eventPhotoMapper.requestToEntity(eventPhotoRequest, new EventPhoto());
    }
    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, EventPhotoMapper eventPhotoMapper, EventService eventService){
        this.photoRepository = photoRepository;
        this.eventPhotoMapper = eventPhotoMapper;
        this.eventService = eventService;

        String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", System.getenv("AccountName"), System.getenv("AccountKey"));

        this.blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(System.getenv("container_name"))
                .buildClient();
    }
    @Override
    public EventPhotoResponse create(EventPhotoRequest eventPhotoRequest) {
        if(eventPhotoRequest != null){
            EventPhoto photo = createModel(eventPhotoRequest);
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
    public List<EventPhotoResponse> uploadPhotos(long eventId, List<MultipartFile> files){
        List<EventPhotoResponse> responses = new ArrayList<>();
            for (MultipartFile file : files) {
                try (ByteArrayInputStream dataStream = new ByteArrayInputStream(file.getBytes())) {
                    long photoId = this.create(new EventPhotoRequest("photoName", "photoUrl", eventId)).getId();

                    BlockBlobClient blockBlobClient = this.blobContainerClient.getBlobClient(photoId + file.getOriginalFilename()).getBlockBlobClient();
                    blockBlobClient.upload(dataStream, file.getSize());

                    String imageName = blockBlobClient.getBlobName();
                    String imageUrl = blockBlobClient.getBlobUrl();
                    responses.add(this.update(new EventPhotoRequest(imageName, imageUrl, eventId), photoId));
                }
                catch (IOException ex){
                    throw new ResponseStatusException("Failed to download images");
                }
            }
        return responses;
    }

}
