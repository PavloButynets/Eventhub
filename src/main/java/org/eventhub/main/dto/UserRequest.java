package org.eventhub.main.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.eventhub.main.model.Event;
import org.eventhub.main.model.Participant;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class UserRequest {
    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    private String firstName;

    @Pattern(regexp = "[A-Z][a-z]+",
            message = "Must start with a capital letter followed by one or more lowercase letters")
    private String lastName;

    @Size(min = 3, max = 15)
    private String username;

    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String email;

    private String profileImage;

    private String description;

    private LocalDateTime createdAt;

    private String city;
}
