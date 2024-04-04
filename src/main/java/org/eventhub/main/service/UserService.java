package org.eventhub.main.service;

import org.eventhub.main.dto.EventSearchResponse;
import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.model.Photo;
import org.eventhub.main.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    UserResponse create(UserRequest userRequest);

    UserResponse readById(UUID id);

    User readByIdEntity(UUID id);

    UserResponse update(UserRequest userRequest);

    void delete(UUID id);

    List<UserResponse> getAll();

    User findByEmail(String email);

    void addImage(UUID id, Photo image);

    void deleteImage(UUID userId, Photo image);
}
