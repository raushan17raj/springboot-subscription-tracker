package com.raushan.apps.subsentry.dto;

import lombok.*;

/**
 * DTO (Data Transfer Object) used to send the JWT access token back to the client
 * after a successful login.
 */
@Getter
@RequiredArgsConstructor
public class JwtAuthResponse {
    private final String accessToken;
    private String tokenType = "Bearer"; // Standard prefix for JWT tokens

}