package com.cabaggregator.authservice.core.dto;

import com.cabaggregator.authservice.core.enums.UserRole;

public record UserRegisterDto(
        String phoneNumber,
        String email,
        String password,
        String firstName,
        String lastName,
        UserRole role
) {
}
