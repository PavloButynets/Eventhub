package org.eventhub.main.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponseBriefInfo {
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    public UserResponseBriefInfo(){

    }

    public UserResponseBriefInfo(String firstName, String lastName, String username, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }
}
