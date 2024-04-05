package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserParticipantResponse {
    private UUID id;
    private UUID userId;
    private LocalDateTime createdAt;
    private PhotoResponse participantPhoto;
    private String firstName;
    private String lastName;
    private String email;

    public UserParticipantResponse(UUID id, UUID userId, LocalDateTime createdAt, PhotoResponse participantPhoto, String firstName, String lastName, String email) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.participantPhoto = participantPhoto;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
