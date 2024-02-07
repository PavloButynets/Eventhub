package org.eventhub.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Pattern(regexp = "[A-Z][a-z]+",
//            message = "Name must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "name")
    private String name;

    @Column(name = "max_participants")
    private int maxParticipants;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @Column(name = "description")
    private String description;

    @Column(name = "participants")
    private int participants;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "location")
    private String location;
}
