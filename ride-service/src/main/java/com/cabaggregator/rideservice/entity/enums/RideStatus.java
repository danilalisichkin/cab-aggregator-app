package com.cabaggregator.rideservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RideStatus {
    REQUESTED("REQUESTED"),
    ARRIVING("ARRIVING"),
    WAITING("WAITING"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String value;
}
