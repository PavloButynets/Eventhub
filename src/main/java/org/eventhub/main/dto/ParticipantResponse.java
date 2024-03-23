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
public class ParticipantResponse {
    private UUID id;
    private LocalDateTime createdAt;
    private UUID userId;
    private PhotoResponse photoResponse;

    public ParticipantResponse(UUID id, LocalDateTime localDateTime, UUID userId, PhotoResponse photoResponse) {
        this.id = id;
        this.createdAt = localDateTime;
        this.userId = userId;
        this.photoResponse = photoResponse;
    }
}