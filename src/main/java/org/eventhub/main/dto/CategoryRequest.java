package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryRequest {
    @NotBlank(message = "Name of Category can't be blank")
    private String name;

    public CategoryRequest() {

    }
    public CategoryRequest(String name){
        this.name = name;
    }
}
