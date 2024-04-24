package org.eventhub.main.mapper;

import org.eventhub.main.dto.RegisterRequest;
import org.eventhub.main.dto.UserRequestCreate;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterMapper {
    private final PhotoMapper photoMapper;
    private final PhotoRepository photoRepository;
    @Autowired
    public RegisterMapper(PhotoMapper photoMapper, PhotoRepository photoRepository){
        this.photoMapper = photoMapper;
        this.photoRepository = photoRepository;
    }

    public UserRequestCreate requestToEntity(RegisterRequest registerRequest, UserRequestCreate userRequest) {
        if(registerRequest == null){
            throw new NullDtoReferenceException("UserRequest can't be null");
        }
        if(userRequest == null){
            throw new NullEntityReferenceException("User can't be null");
        }

        userRequest.setFirstName(registerRequest.getFirstName());
        userRequest.setLastName(registerRequest.getLastName());
        userRequest.setUsername(registerRequest.getUsername());
        userRequest.setEmail(registerRequest.getEmail());
        userRequest.setPassword(registerRequest.getPassword());
        userRequest.setCity(registerRequest.getCity());
        userRequest.setGender(registerRequest.getGender());
        return userRequest;
    }
}