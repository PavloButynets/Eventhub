package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.eventhub.main.model.Category;
import org.eventhub.main.model.EventPhoto;
import org.eventhub.main.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventRequest {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 20, min = 5,
            message = "Name length cannot be greater than 20 symbols")
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
    private String location;

    @NotNull
    private List<CategoryRequest> categoryRequests;

    @NotNull
    private long ownerId;
}
