package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;


@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventFilterRequest {
    private int minParticipants;
    private int maxParticipants;
    private LocalDateTime startAt;
    private LocalDateTime expireAt;
    private String location;
    private List<CategoryRequest> categoryRequests;

    public EventFilterRequest(){}

    public EventFilterRequest(int minParticipants, int maxParticipants, LocalDateTime startAt, LocalDateTime expireAt, String location, List<CategoryRequest> categoryRequests) {
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.startAt = startAt;
        this.expireAt = expireAt;
        this.location = location;
        this.categoryRequests = categoryRequests;
    }
}