package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ParticipantWithPhotoResponse {
    @NotNull
    private UUID id;

    @NotNull
    private UUID userId;

    @NotBlank(message = "The 'URL' cannot be empty")
    private String photoUrl;

    public ParticipantWithPhotoResponse(UUID id, UUID userId, String photoUrl) {
        this.id = id;
        this.userId = userId;
        this.photoUrl = photoUrl;
    }
}
