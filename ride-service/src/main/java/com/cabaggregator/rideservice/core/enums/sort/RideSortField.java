package com.cabaggregator.rideservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RideSortField {
    ID("id"),
    ORDERED("orderTime"),
    STARTED("startTime"),
    ENDED("endTime"),
    PRICE("price"),
    DURATION("estimatedDuration");

    private final String value;
}