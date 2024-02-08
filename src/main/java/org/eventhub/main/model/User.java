package org.eventhub.main.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Size(min = 3, max = 15)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "[A-Za-z\\d]{6,}",
            message = "Must be minimum 6 symbols long, using digits and latin letters")
    @Pattern(regexp = ".*\\d.*",
            message = "Must contain at least one digit")
    @Pattern(regexp = ".*[A-Z].*",
            message = "Must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*",
            message = "Must contain at least one lowercase letter")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "photo_link", nullable = false)
    private String photoLink;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "location_city", nullable = false)
    private String city;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "owner")
    private List<Event> userEvents;

    @OneToMany(mappedBy = "user")
    private List<Participant> userParticipants;
}
