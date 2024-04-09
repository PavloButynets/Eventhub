package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.eventhub.main.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventSearchResponse {

    @NotNull
    private UUID id;

    @NotNull
    private UUID ownerId;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull
    private int maxParticipants;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime expireAt;

    @NotNull
    private int participantCount;

    @NotNull
    private String location;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @NotNull
    PhotoResponse photoResponse;
    public EventSearchResponse() {
    }

    public EventSearchResponse(UUID id, UUID ownerId, String title, int maxParticipants, LocalDateTime startAt, LocalDateTime expireAt, int participantCount, String location, BigDecimal latitude, BigDecimal longitude, PhotoResponse photoResponse) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.maxParticipants = maxParticipants;
        this.startAt = startAt;
        this.expireAt = expireAt;
        this.participantCount = participantCount;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoResponse = photoResponse;
    }
}
