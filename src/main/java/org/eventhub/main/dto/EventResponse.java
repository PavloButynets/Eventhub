package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.eventhub.main.model.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventResponse {

    @NotNull
    private long id;

    @NotNull
    private String title;

    @NotNull
    private int maxParticipants;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime expireAt;

    @NotNull
    private String description;

    @NotNull
    private int participantCount;

    @NotNull
    private State state;

    @NotNull
    private String location;

    @NotNull
    private long ownerId;
}
