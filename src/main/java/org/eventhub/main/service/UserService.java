package org.eventhub.main.service;

import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.model.User;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest userRequest);

    UserResponse readByDtoId(long id);

    User readById(long id);

    UserResponse update(UserRequest userRequest);

    void delete(long id);

    List<UserResponse> getAll();
}
