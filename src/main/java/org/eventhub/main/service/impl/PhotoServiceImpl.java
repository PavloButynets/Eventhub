package org.eventhub.main.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.eventhub.main.dto.EventPhotoRequest;
import org.eventhub.main.dto.EventPhotoResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.mapper.EventPhotoMapper;
import org.eventhub.main.model.EventPhoto;
import org.eventhub.main.repository.PhotoRepository;
import org.eventhub.main.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {
    private final PhotoRepository photoRepository;
    private final EventPhotoMapper eventPhotoMapper;


    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, EventPhotoMapper eventPhotoMapper){
        this.photoRepository = photoRepository;
        this.eventPhotoMapper = eventPhotoMapper;
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
                .orElseThrow(()->new EntityNotFoundException("Photo with" + id + " id is not found"));
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
        photoRepository.delete(readByIdEntity(id));
    }

    @Override
    public List<EventPhotoResponse> getAll() {
        return photoRepository.findAll()
                .stream()
                .map(eventPhotoMapper::entityToResponse)
                .collect(Collectors.toList());
    }
}
