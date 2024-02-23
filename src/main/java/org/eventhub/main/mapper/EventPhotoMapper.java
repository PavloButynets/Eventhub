package org.eventhub.main.mapper;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import org.eventhub.main.dto.CategoryRequest;
import org.eventhub.main.dto.CategoryResponse;
import org.eventhub.main.dto.EventPhotoRequest;
import org.eventhub.main.dto.EventPhotoResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Category;
import org.eventhub.main.model.EventPhoto;
import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class EventPhotoMapper {
    private final EventRepository eventRepository;
    private final BlobContainerClient blobContainerClient;
    @Autowired
    public EventPhotoMapper(EventRepository eventRepository){
        this.eventRepository = eventRepository;
        String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", System.getenv("AccountName"), System.getenv("AccountKey"));

        blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(System.getenv("container_name"))
                .buildClient();
    }
    public EventPhotoResponse entityToResponse(EventPhoto eventPhoto) {
        if (eventPhoto == null) {
            throw new NullEntityReferenceException("Event Photo can't be null");
        }

        OffsetDateTime expiryTime = OffsetDateTime.now().plusDays(1);
        BlobSasPermission sasPermission = new BlobSasPermission()
                .setReadPermission(true);
        BlobServiceSasSignatureValues sasSignatureValues = new BlobServiceSasSignatureValues(expiryTime, sasPermission)
                .setStartTime(OffsetDateTime.now().minusMinutes(5));

        String sasToken = blobContainerClient.generateSas(sasSignatureValues);

        return EventPhotoResponse.builder()
                .id(eventPhoto.getId())
                .photoName(eventPhoto.getPhotoName())
                .photoUrl(eventPhoto.getPhotoUrl() +"?"+sasToken)
                .eventId(eventPhoto.getEvent().getId())
                .build();
    }

    public EventPhoto requestToEntity(EventPhotoRequest request, EventPhoto eventPhoto){
        if(request == null){
            throw new NullDtoReferenceException("Request can't be null");
        }
        if(eventPhoto == null){
            throw new NullEntityReferenceException("Event Photo can't be null");
        }

        eventPhoto.setPhotoUrl(request.getPhotoUrl());
        eventPhoto.setPhotoName(request.getPhotoName());
        eventPhoto.setEvent(eventRepository.findById(request.getEventId()).orElseThrow(()->new NullEntityReferenceException("Event can't be null")));
        return eventPhoto;
    }

}
