package org.eventhub.main.service;

import org.eventhub.main.dto.UserDto;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.model.User;

import java.util.List;

public interface UserService {

    UserDto create(UserRequest userRequest);

    UserDto readByDtoId(long id);

    User readById(long id);

    UserDto update(UserRequest userRequest);

    void delete(long id);

    List<UserDto> getAll();
}
