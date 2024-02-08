package org.eventhub.main.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParticipantResponse {

    private final Long eventId;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final boolean isApproved;

}
