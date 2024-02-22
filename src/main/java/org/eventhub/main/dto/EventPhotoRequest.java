package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventPhotoRequest {
    @NotBlank(message = "The 'URL' cannot be empty")
    private String photoUrl;

    @NotBlank(message = "The 'Name' cannot be empty")
    private String photoName;

    private UUID eventId;

    public EventPhotoRequest(){}

    public EventPhotoRequest(String photoName, String photoUrl,  UUID eventId) {
        this.photoUrl = photoUrl;
        this.photoName = photoName;
        this.eventId = eventId;
    }
}
