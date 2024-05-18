package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PasswordRequest {
    @NotNull
    private String oldPassword;

    @Pattern(regexp = "[A-Za-z\\d]{6,}",
            message = "Password must be minimum 6 symbols long, using digits and latin letters")
    @Pattern(regexp = ".*\\d.*",
            message = "Password must contain at least one digit")
    @Pattern(regexp = ".*[A-Z].*",
            message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*",
            message = "Password must contain at least one lowercase letter")
    private String newPassword;

    public PasswordRequest(){

    }
    public PasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}