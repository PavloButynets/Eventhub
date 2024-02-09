package org.eventhub.main.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.eventhub.main.model.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EventDto {

    private long id;

    private String title;

    private int maxParticipants;

    private LocalDateTime createdAt;

    private LocalDateTime startAt;

    private LocalDateTime expireAt;

    private String description;

    private int participantCount;

    private State state;

    private String location;

    private List<EventPhoto> photos;

    private User owner;

    private List<Participant> participants;

    private List<Category> categories;
}
