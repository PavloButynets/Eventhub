package org.eventhub.main.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ParticipantResponse {
    private Long id;
    private Long eventId;
    private Long userId;
    private LocalDateTime createdAt;
    private boolean isApproved;

}