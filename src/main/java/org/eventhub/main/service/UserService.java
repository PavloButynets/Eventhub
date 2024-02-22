package org.eventhub.main.service;

import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    UserResponse create(UserRequest userRequest);

    UserResponse readById(long id);

    User readByIdEntity(long id);

    UserResponse update(UserRequest userRequest);

    void delete(long id);

    List<UserResponse> getAll();

    User findByEmail(String email);
}
