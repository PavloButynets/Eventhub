package org.eventhub.main.mapper;

import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Participant;
import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ParticipantMapper {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public ParticipantMapper(UserRepository userRepository, EventRepository eventRepository){
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }
    public ParticipantResponse entityToResponse(Participant participant) {
        if (participant == null) {
            throw new NullEntityReferenceException("Participant can't be null");
        }
        return ParticipantResponse.builder()
                .id(participant.getId())
                .createdAt(participant.getCreatedAt())
                .isApproved((participant.isApproved()))
                .eventId(participant.getEvent().getId())
                .userId(participant.getUser().getId())
                .build();
    }

    public Participant requestToEntity(ParticipantRequest participantRequest){
        if(participantRequest == null){
            throw new NullDtoReferenceException("Request can't be null");
        }

        Participant participant = new Participant();
        participant.setId(participantRequest.getId());
        participant.setApproved(participantRequest.isApproved());
        participant.setUser(userRepository.findById(participant.getId()).orElseThrow(()->new NullEntityReferenceException("User cant be null")));
        participant.setEvent(eventRepository.findById(participantRequest.getEventId()).orElseThrow(()->new NullEntityReferenceException("Event cant be null")));
        return participant;
    }
}
