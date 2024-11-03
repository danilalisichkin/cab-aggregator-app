package com.cabaggregator.rideservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceCategory {
    ECONOM("ECONOM"),
    COMFORT("COMFORT"),
    BUSINESS("BUSINESS");

    private final String value;
}
