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
public class CategoryResponse {
    private UUID id;
    @NotBlank(message = "Name of Category can't be blank")
    private String name;

    public CategoryResponse() {
    }

    public CategoryResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
