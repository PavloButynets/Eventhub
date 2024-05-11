package org.eventhub.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {

    private String accessToken;
    private String refreshToken;
    private Date expiryDate;
}
