package org.eventhub.main.mapper;

import org.eventhub.main.dto.UserDto;
import org.eventhub.main.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileImage(),
                user.getDescription(),
                user.getCreatedAt(),
                user.getCity(),
                user.getUserEvents(),
                user.getUserParticipants()
        );
    }
}
