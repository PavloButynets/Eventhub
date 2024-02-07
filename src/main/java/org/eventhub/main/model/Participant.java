package org.eventhub.main.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(mappedBy = "participants")
    private Event event;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User participant;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_approved")
    private boolean isApproved;
}
