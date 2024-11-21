package com.cabaggregator.authservice.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeycloakRole {
    ADMIN("ADMIN"),
    PASSENGER("PASSENGER"),
    DRIVER("DRIVER");

    private final String value;
}
