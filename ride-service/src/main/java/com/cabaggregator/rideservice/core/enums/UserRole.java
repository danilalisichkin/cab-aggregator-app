package com.cabaggregator.rideservice.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    DRIVER("DRIVER"),
    PASSENGER("PASSENGER"),
    ADMIN("ADMIN");

    private final String value;

    public static UserRole fromValue(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        return null;
    }
}
