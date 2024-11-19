package com.cabaggregator.authservice.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KeyCloakRole {
    ADMIN("ADMIN"),
    PASSENGER("PASSENGER"),
    DRIVER("DRIVER");

    private final String value;
}
