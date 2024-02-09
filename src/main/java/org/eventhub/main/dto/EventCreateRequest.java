package org.eventhub.main.dto;

import lombok.Data;
import org.eventhub.main.model.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventCreateRequest {

    private String title;

    private int maxParticipants;

    private LocalDateTime startAt;

    private LocalDateTime expireAt;

    private String description;

    private String location;

    private List<EventPhoto> photos;

    private User owner;

    private List<Category> categories;
}
