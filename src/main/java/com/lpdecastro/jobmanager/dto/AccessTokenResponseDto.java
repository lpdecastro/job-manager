package com.lpdecastro.jobmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponseDto {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
}
