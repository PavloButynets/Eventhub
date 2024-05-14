package org.eventhub.main.mapper;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
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
        userRequest.setProvider(registerRequest.getProvider());
        userRequest.setVerified(registerRequest.isVerified());
        if (registerRequest.getPhotoUrl() != null) {
            userRequest.setPhotoUrl(registerRequest.getPhotoUrl());
        }

        return userRequest;
    }
    public RegisterRequest googlePayloadToRegisterRequest(GoogleIdToken.Payload payload) {

        return RegisterRequest.builder()
                .email(payload.getEmail())
                .firstName((String) payload.get("given_name"))
                .lastName((String) payload.get("family_name"))
                .provider("Google")
                .username((String) payload.get("given_name") + (String) payload.get("family_name"))
                .photoUrl((String) payload.get("picture"))
                .isVerified(payload.getEmailVerified())
                .build();
    }

}