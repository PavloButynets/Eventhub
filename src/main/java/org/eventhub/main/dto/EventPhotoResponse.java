package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventPhotoResponse {
    private UUID id;
    @NotBlank(message = "The 'URL' cannot be empty")
    private String photoUrl;

    @NotBlank(message = "The 'Name' cannot be empty")
    private String photoName;

    private UUID eventId;
}
