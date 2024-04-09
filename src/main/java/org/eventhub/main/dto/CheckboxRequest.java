package org.eventhub.main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CheckboxRequest {
    private UUID userId;

    @JsonProperty("is_my_events")
    private boolean isMyEvents;

    @JsonProperty("is_joined_events")
    private boolean isJoinedEvents;

    @JsonProperty("is_pending_events")
    private boolean isPendingEvents;

    @JsonProperty("is_archive_events")
    private boolean isArchiveEvents;

    public CheckboxRequest(UUID userId, boolean isMyEvents, boolean isJoinedEvents, boolean isPendingEvents, boolean isArchiveEvents) {
        this.userId = userId;
        this.isMyEvents = isMyEvents;
        this.isJoinedEvents = isJoinedEvents;
        this.isPendingEvents = isPendingEvents;
        this.isArchiveEvents = isArchiveEvents;
    }

    public CheckboxRequest() {
    }
}
