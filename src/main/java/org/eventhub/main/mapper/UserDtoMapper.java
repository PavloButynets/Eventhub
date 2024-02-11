package org.eventhub.main.mapper;

import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserDtoMapper {

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;

    public UserDtoMapper(ParticipantRepository participantRepository, EventRepository eventRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
    }

    public UserResponse UserToResponse(User user) {
        return new UserResponse(
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

    public User RequestToUser(UserRequest userRequest) {
        return new User(
                userRequest.getId(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getProfileImage(),
                userRequest.getDescription(),
                LocalDateTime.now(),
                userRequest.getCity(),
                userRequest.getPhoneNumber(),
                eventRepository.findAll(),
                participantRepository.findAll()
        );
    }
}
