package com.cabaggregator.rideservice.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    DRIVER("DRIVER"),
    PASSENGER("PASSENGER");

    private final String value;
}
