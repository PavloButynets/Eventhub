package org.eventhub.main.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.Participant;

import java.time.LocalDateTime;
import java.util.List;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String profileImage,
        String description,
        LocalDateTime createdAt,
        String city,
        List<Event> userEvents,
        List<Participant> userParticipants
) {
}
