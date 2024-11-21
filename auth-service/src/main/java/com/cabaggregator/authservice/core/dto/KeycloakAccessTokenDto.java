package com.cabaggregator.authservice.core.dto;

public record KeycloakAccessTokenDto(
        String accessToken,
        long expiresIn,
        long refreshExpiresIn,
        String refreshToken,
        String tokenType
) {
}
