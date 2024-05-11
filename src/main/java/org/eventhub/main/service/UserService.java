package org.eventhub.main.service;

import org.eventhub.main.dto.*;
import org.eventhub.main.model.Photo;
import org.eventhub.main.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    UserResponse create(UserRequestCreate userRequest);

    UserResponse readById(UUID id);

    User readByIdEntity(UUID id);

    UserResponse readByUsername(String username);

    String getUsername(UUID id);
    UserResponse update(UUID id, UserRequestUpdate userRequest);

    void delete(UUID id);

    List<UserResponse> getAll();

    User findByEmail(String email);

    void addImage(UUID id, Photo image);

    void deleteImage(UUID userId, Photo image);
    UserResponse changePassword(UUID userId, PasswordRequest passwordRequest);

    UserResponse confirmUser(UUID id);
    List<UserResponseBriefInfo> findApprovedUsersByEventId(UUID eventId);
}
