package org.eventhub.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 20,
    message = "Name length cannot be greater than 20 symbols")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Specify max number of participants")
    @Pattern(regexp = "\\d+",
            message = "This field must contain only numbers")
    @Column(name = "max_participants")
    private int maxParticipants;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "start_at")
    private LocalDateTime startAt;

    @NotNull
    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 255,
            message = "Description length cannot be greater than 255 symbols")
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "participant_count")
    private int participantCount;

    @NotNull
    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "event")
    private List<Photo> photos;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(name="event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "participants_id"))
    private List<User> participants;

    @ManyToMany
    @JoinTable(name = "event_categories",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

}
