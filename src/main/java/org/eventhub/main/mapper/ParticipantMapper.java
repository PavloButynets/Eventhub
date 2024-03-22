package org.eventhub.main.mapper;

import org.eventhub.main.dto.ParticipantRequest;
import org.eventhub.main.dto.ParticipantResponse;
import org.eventhub.main.dto.ParticipantWithPhotoResponse;
import org.eventhub.main.dto.PhotoResponse;
import org.eventhub.main.exception.NullDtoReferenceException;
import org.eventhub.main.exception.NullEntityReferenceException;
import org.eventhub.main.model.Participant;
import org.eventhub.main.repository.EventRepository;
import org.eventhub.main.repository.UserRepository;
import org.eventhub.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
                .isApproved((participant.isApproved()))
                .eventId(participant.getEvent().getId())
                .userId(participant.getUser().getId())
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

    public ParticipantWithPhotoResponse entityToPhotoResponse(Participant participant) {
        ParticipantWithPhotoResponse response = ParticipantWithPhotoResponse.builder()
                .id(participant.getId())
                .userId(participant.getUser().getId())
                .build();
        List<PhotoResponse> photoResponses = userService.readById(participant.getUser().getId()).getPhotoResponses();
        String photoUrl =  (photoResponses.isEmpty()) ? "https://eventhub12.blob.core.windows.net/images/userDefault.jpeg?sp=r&st=2024-03-22T11:44:03Z&se=2024-03-31T18:44:03Z&spr=https&sv=2022-11-02&sr=b&sig=QWt0H3SUlf34ITOzw2lWV4EdIUz1bZ3MZg94jEw9N6o%3D" : photoResponses.get(0).getPhotoUrl();
        response.setPhotoUrl(photoUrl);
        return response;
    }
}
