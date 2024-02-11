package org.eventhub.main.mapper;

import org.eventhub.main.dto.UserResponse;
import org.eventhub.main.dto.UserRequest;
import org.eventhub.main.model.User;
import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final EventDtoMapper eventDtoMapper;
    private final ParticipantMapper participantMapper;

    public UserMapper(ParticipantRepository participantRepository, EventRepository eventRepository, EventDtoMapper eventDtoMapper, ParticipantMapper participantMapper) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.eventDtoMapper = eventDtoMapper;
        this.participantMapper = participantMapper;
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
                user.getBirthDate(),
                user.getGender()
//                user.getUserEvents().stream().map(eventDtoMapper::EventToResponse).collect(Collectors.toList()),
//                //Yaroslav should implement EventToResponse in his mapper
//                user.getUserParticipants().stream().map(participantMapper::entityToResponse).collect(Collectors.toList())
        );
    }

    public User RequestToUser(UserRequest userRequest, User user) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setProfileImage(userRequest.getProfileImage());
        user.setDescription(userRequest.getDescription());
        user.setCreatedAt(LocalDateTime.now());
        user.setCity(userRequest.getCity());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setBirthDate(userRequest.getBirthDate());
        user.setGender(userRequest.getGender());
//        user.setUserEvents(userRequest.getUserEvents().stream().map(eventDtoMapper::RequestToEvent).collect(Collectors.toList()));
//        //Yaroslav should implement RequestToEvent in his mapper
//        user.setUserParticipants(userRequest.getUserParticipants().stream().map(participantMapper::requestToEntity).collect(Collectors.toList()));

        return user;
    }
}
