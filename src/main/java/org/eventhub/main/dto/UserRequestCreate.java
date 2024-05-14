package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.eventhub.main.model.Gender;

import java.time.LocalDate;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserRequestCreate {
    @Pattern(regexp = "\\p{Lu}\\p{Ll}+",
            message = "First Name must start with a capital letter followed by one or more lowercase letters")
    private String firstName;

    @Pattern(regexp = "\\p{Lu}\\p{Ll}+",
            message = "Last Name must start with a capital letter followed by one or more lowercase letters")
    private String lastName;

    @Size(min = 3, max = 20)
    private String username;

    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String email;

    @Pattern(regexp = "[A-Za-z\\d]{6,}",
            message = "Must be minimum 6 symbols long, using digits and latin letters")
    @Pattern(regexp = ".*\\d.*",
            message = "Must contain at least one digit")
    @Pattern(regexp = ".*[A-Z].*",
            message = "Must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*",
            message = "Must contain at least one lowercase letter")
    private String password;

    private String city;

    private String provider;

    private String photoUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean isVerified;

    public UserRequestCreate() {}

    public UserRequestCreate(String firstName, String lastName, String username, String email, String password, String city, String provider, String photoUrl, Gender gender, boolean isVerified) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.city = city;
        this.provider = provider;
        this.photoUrl = photoUrl;
        this.gender = gender;
        this.isVerified = isVerified;
    }
}