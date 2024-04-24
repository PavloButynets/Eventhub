package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.eventhub.main.model.ParticipantState;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ParticipantStateResponse {
    private ParticipantState state;
    private boolean isOwner;

    public ParticipantStateResponse(ParticipantState state, boolean isOwner) {
        this.state = state;
        this.isOwner = isOwner;
    }
}
