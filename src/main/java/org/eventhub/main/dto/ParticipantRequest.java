package org.eventhub.main.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ParticipantRequest {
    @NonNull
    private Long eventId;
    @NonNull
    private Long userId;
    private Boolean isApproved;

    public ParticipantRequest(Long eventId, Long userId, Boolean isApproved){
        this.eventId = eventId;
        this.userId = userId;
        this.isApproved = isApproved;
    }
}