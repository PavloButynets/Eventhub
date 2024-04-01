package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventResponseXY {
    @NotNull
    private UUID id;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    public EventResponseXY() {
    }

    public EventResponseXY(UUID id, BigDecimal latitude, BigDecimal longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
