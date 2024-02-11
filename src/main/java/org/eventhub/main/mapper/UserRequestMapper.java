package org.eventhub.main.mapper;

import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.repository.ParticipantRepository;
import org.eventhub.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserRequestMapper implements Function<UserRequest, User> {
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;

    public UserRequestMapper(ParticipantRepository participantRepository, EventRepository eventRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public User apply(UserRequest userRequest) {
        return new User(
                userRequest.getId(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getProfileImage(),
                userRequest.getDescription(),
                userRequest.getCreatedAt(),
                userRequest.getCity(),
                userRequest.getPhoneNumber(),
                eventRepository.findAll(),
                participantRepository.findAll()
        );
    }
}
