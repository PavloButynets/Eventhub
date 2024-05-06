package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.eventhub.main.model.Gender;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterRequest {
    @Pattern(regexp = "\\p{Lu}\\p{Ll}+",
            message = "First name must start with a capital letter followed by one or more lowercase letters")
    private String firstName;

    @Pattern(regexp = "\\p{Lu}\\p{Ll}+",
            message = "Last name must start with a capital letter followed by one or more lowercase letters")
    private String lastName;

    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 symbols")
    private String username;

    @Pattern(regexp = "[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", message = "Must be a valid e-mail address")
    private String email;

    @Pattern(regexp = "[A-Za-z\\d]{6,}",
            message = "Password must be minimum 6 symbols long, using digits and latin letters")
    @Pattern(regexp = ".*\\d.*",
            message = "Password must contain at least one digit")
    @Pattern(regexp = ".*[A-Z].*",
            message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*",
            message = "Password must contain at least one lowercase letter")
    private String password;

    private String city;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public RegisterRequest() {}

    public RegisterRequest(String firstName, String lastName, String username, String email, String password, String city, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.city = city;
        this.gender = gender;
    }
}
