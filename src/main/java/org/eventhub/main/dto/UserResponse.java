package org.eventhub.main.dto;

import org.eventhub.main.model.Gender;

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
        Gender gender
) {
}
