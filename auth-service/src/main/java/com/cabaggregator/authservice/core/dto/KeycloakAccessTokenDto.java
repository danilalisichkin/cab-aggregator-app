package com.cabaggregator.authservice.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Server response with user's access token")
public record KeycloakAccessTokenDto(
        String accessToken,
        long expiresIn,
        long refreshExpiresIn,
        String refreshToken,
        String tokenType
) {
}
