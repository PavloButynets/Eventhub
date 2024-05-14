package org.eventhub.main.mapper;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import org.eventhub.main.dto.PhotoRequest;
import org.eventhub.main.dto.PhotoResponse;
import org.eventhub.main.exception.AccessIsDeniedException;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Photo;
import org.eventhub.main.utility.BlobContainerClientSingleton;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class PhotoMapper {
    private final BlobContainerClient blobContainerClientEvent;
    private final BlobContainerClient blobContainerClientUser;
    public PhotoMapper(){
        this.blobContainerClientEvent = BlobContainerClientSingleton.getInstance().getBlobContainerClientEvent();
        this.blobContainerClientUser = BlobContainerClientSingleton.getInstance().getBlobContainerClientUser();
    }
    public PhotoResponse entityToResponse(Photo photo) {
        if (photo == null) {
            throw new NullEntityReferenceException("Photo can't be null");
        }
        if (photo.getPhotoName().startsWith("GPhoto")) {
            return PhotoResponse.builder()
                    .id(photo.getId())
                    .photoName(photo.getPhotoName())
                    .photoUrl(photo.getPhotoUrl())
                    .build();
        }
        OffsetDateTime expiryTime = OffsetDateTime.now().plusDays(1);
        BlobSasPermission sasPermission = new BlobSasPermission()
                .setReadPermission(true);
        BlobServiceSasSignatureValues sasSignatureValues = new BlobServiceSasSignatureValues(expiryTime, sasPermission)
                .setStartTime(OffsetDateTime.now().minusMinutes(5));

        String sasToken ;
        if(photo.getPhotoName().startsWith("event")) {
            sasToken = blobContainerClientEvent.generateSas(sasSignatureValues);
        }
        else if(photo.getPhotoName().startsWith("user")){
            sasToken = blobContainerClientUser.generateSas(sasSignatureValues);
        }
        else{
            throw new AccessIsDeniedException("Failed to generate sas token");
        }

        return PhotoResponse.builder()
                .id(photo.getId())
                .photoName(photo.getPhotoName())
                .photoUrl(photo.getPhotoUrl() +"?"+sasToken)
                .build();
    }
    public Photo requestToEntity(PhotoRequest request, Photo photo){
        if(request == null){
            throw new NullDtoReferenceException("Request can't be null");
        }
        if(photo == null){
            throw new NullEntityReferenceException("Event Photo can't be null");
        }

        photo.setPhotoUrl(request.getPhotoUrl());
        photo.setPhotoName(request.getPhotoName());
        return photo;
    }

}
