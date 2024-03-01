package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eventhub.main.model.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String description;
    private LocalDateTime createdAt;
    private String city;
    private LocalDate birthDate;
    private Gender gender;

    public UserResponse(){

    }

    public UserResponse(UUID id, String firstName, String lastName, String username, String email, String description, LocalDateTime createdAt, String city, LocalDate birthDate, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.description = description;
        this.createdAt = createdAt;
        this.city = city;
        this.birthDate = birthDate;
        this.gender = gender;
    }

}
