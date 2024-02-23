package org.eventhub.main.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ParticipantRequest {
    @NonNull
    private UUID eventId;

    @NonNull
    private UUID userId;

    private Boolean isApproved;

    public ParticipantRequest(UUID eventId, UUID userId, Boolean isApproved){
        this.eventId = eventId;
        this.userId = userId;
        this.isApproved = isApproved;
    }
}