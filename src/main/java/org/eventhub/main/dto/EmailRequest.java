package org.eventhub.main.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmailRequest {
    private String to;
    private String subject;
    private String body;
    private String name;
    public EmailRequest(){

    }
    public EmailRequest(String to, String subject, String body, String name){
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.name = name;
    }
}
