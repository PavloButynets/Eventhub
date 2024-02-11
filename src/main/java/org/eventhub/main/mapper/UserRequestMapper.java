package org.eventhub.main.mapper;

import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserRequestMapper implements Function<UserRequest, User> {
    @Override
    public User apply(UserRequest userRequest) {
        return new User(
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getProfileImage(),
                userRequest.getDescription(),
                userRequest.getCreatedAt(),
                userRequest.getCity()
        );
    }
}
