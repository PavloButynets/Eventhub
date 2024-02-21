package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Title cannot be blank")
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

    @NotNull
    private List<CategoryResponse> categoryResponses;

    public EventResponse() {
    }

    public EventResponse(long id, String title, int maxParticipants, LocalDateTime createdAt, LocalDateTime startAt, LocalDateTime expireAt, String description, int participantCount, State state, String location, long ownerId, List<CategoryResponse> categoryResponses) {
        this.id = id;
        this.title = title;
        this.maxParticipants = maxParticipants;
        this.createdAt = createdAt;
        this.startAt = startAt;
        this.expireAt = expireAt;
        this.description = description;
        this.participantCount = participantCount;
        this.state = state;
        this.location = location;
        this.ownerId = ownerId;
        this.categoryResponses = categoryResponses;
    }
}
