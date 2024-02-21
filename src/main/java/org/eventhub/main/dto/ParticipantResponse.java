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
    private boolean isApproved;
    private UUID eventId;
    private UUID userId;

    public ParticipantResponse(UUID id, LocalDateTime localDateTime, boolean isApproved, UUID eventId, UUID userId) {
        this.id = id;
        this.createdAt = localDateTime;
        this.isApproved = isApproved;
        this.eventId = eventId;
        this.userId = userId;
    }
}