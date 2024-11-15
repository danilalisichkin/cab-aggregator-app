package com.cabaggregator.authservice.core.dto;

public record UserLoginDto(
        String identifier,
        String password
) {
}
