package org.eventhub.main.mapper;

import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Participant;
import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ParticipantMapper {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final UserService userService;

    @Autowired
    public ParticipantMapper(UserRepository userRepository, EventRepository eventRepository, UserService userService){
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.userService = userService;
    }
    public ParticipantResponse entityToResponse(Participant participant) {
        if (participant == null) {
            throw new NullEntityReferenceException("Participant can't be null");
        }
        return ParticipantResponse.builder()
                .id(participant.getId())
                .createdAt(participant.getCreatedAt())
                .userId(participant.getUser().getId())
                .participantPhoto(userService.readById(participant.getUser().getId()).getPhotoResponses().get(0))
                .build();
    }

    public Participant requestToEntity(ParticipantRequest participantRequest, Participant participant){
        if(participantRequest == null){
            throw new NullDtoReferenceException("Request can't be null");
        }
        if(participant == null){
            throw  new NullEntityReferenceException("Participant cat't be null");
        }

        participant.setUser(userRepository.findById(participantRequest.getUserId()).orElseThrow(()->new NullEntityReferenceException("User cant be null")));
        participant.setEvent(eventRepository.findById(participantRequest.getEventId()).orElseThrow(()->new NullEntityReferenceException("Event cant be null")));
        return participant;
    }
}
