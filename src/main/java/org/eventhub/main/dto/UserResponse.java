package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.eventhub.main.model.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
//@Builder
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String profileImage;
    private String description;
    private LocalDateTime createdAt;
    private String city;
    private LocalDate birthDate;
    private Gender gender;
}
