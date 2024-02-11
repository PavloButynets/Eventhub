package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.eventhub.main.model.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventResponse {

    private long id;

    private String title;

    private int maxParticipants;

    private LocalDateTime createdAt;

    private LocalDateTime startAt;

    private LocalDateTime expireAt;

    private String description;

    private int participantCount;

    private State state;

    private String location;

    private long ownerId;
}
