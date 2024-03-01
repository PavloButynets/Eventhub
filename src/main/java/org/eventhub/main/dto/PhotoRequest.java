package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PhotoRequest {
    @NotBlank(message = "The 'URL' cannot be empty")
    private String photoUrl;

    @NotBlank(message = "The 'Name' cannot be empty")
    private String photoName;

    public PhotoRequest(){}

    public PhotoRequest(String photoName, String photoUrl) {
        this.photoUrl = photoUrl;
        this.photoName = photoName;
    }
}
