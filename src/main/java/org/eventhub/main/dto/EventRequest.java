package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 35, min = 5,
            message = "Event name must be between 5 and 35 characters")
    private String title;

    @NotNull
    @Max(value = 20000,
            message = "You cannot have more than 20 000 participants")
    @Min(value = 2,
            message = "You cannot have less than 2 participants")
    private int maxParticipants;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime expireAt;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 255,
            message = "Description length cannot be greater than 255 symbols")
    private String description;

    @NotNull
    @NotEmpty(message = "Please choose correct location")
    @NotBlank(message = "Please choose correct location")
    private String location;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @NotNull
    private boolean withOwner;
  
    @NotNull
    @Size(min = 1, message = "Please choose at least one category!")
    private List<CategoryRequest> categoryRequests;

    @NotNull
    private UUID ownerId;


    public EventRequest(){}

    public EventRequest(String title, int maxParticipants, LocalDateTime startAt, LocalDateTime expireAt, String description, String location, BigDecimal latitude, BigDecimal longitude, boolean withOwner, List<CategoryRequest> categoryRequests, UUID ownerId) {
        this.title = title;
        this.maxParticipants = maxParticipants;
        this.startAt = startAt;
        this.expireAt = expireAt;
        this.description = description;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.withOwner = withOwner;
        this.categoryRequests = categoryRequests;
        this.ownerId = ownerId;
    }
}
