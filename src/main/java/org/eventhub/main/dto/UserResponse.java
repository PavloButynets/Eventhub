package org.eventhub.main.dto;

import org.eventhub.main.model.Event;
import org.eventhub.main.model.Participant;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String profileImage,
        String description,
        LocalDateTime createdAt,
        String city,
        List<EventDto> userEvents,
        List<ParticipantResponse> userParticipants
) {
}
