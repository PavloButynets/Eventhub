package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.eventhub.main.model.Category;
import org.eventhub.main.model.EventPhoto;
import org.eventhub.main.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventRequest {
    @NotNull
    private String title;

    @NotNull
    private int maxParticipants;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime expireAt;

    @NotNull
    private String description;

    @NotNull
    private String location;

    @NotNull
    private long ownerId;
}
