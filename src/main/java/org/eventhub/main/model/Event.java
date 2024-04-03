package org.eventhub.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.engine.internal.Cascade;

import org.hibernate.engine.internal.Cascade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 20, min = 5,
    message = "Name length cannot be greater than 20 symbols")
    @Column(name = "title", unique = true)
    private String title;

    @NotNull
    @Max(value = 20000,
            message = "You cannot have more than 20 000 participants")
    @Min(value = 2,
            message = "You cannot have less than 2 participants")
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

    @NotNull
    @Column(name = "latitude", precision = 8, scale = 6)
    private BigDecimal latitude;

    @NotNull
    @Column(name = "longitude", precision = 9, scale = 6)
    private BigDecimal longitude;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Photo> photos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
    private List<Participant> participants;

    @ManyToMany
    @JoinTable(name = "event_categories",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "embedding_id")
    private Embedding embedding;
}
