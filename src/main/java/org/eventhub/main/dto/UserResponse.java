package org.eventhub.main.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Past;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.Gender;
import org.eventhub.main.model.Participant;

import java.time.LocalDate;
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
        LocalDate birthDate,
        Gender gender,
        List<EventDto> userEvents,
        List<ParticipantResponse> userParticipants
) {
}
