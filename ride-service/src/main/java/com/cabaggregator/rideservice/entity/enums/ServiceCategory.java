package com.cabaggregator.rideservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ServiceCategory {
    ECONOM(1, "ECONOM"),
    COMFORT(2, "COMFORT"),
    BUSINESS(3, "BUSINESS");

    private final int id;
    private final String value;

    public static ServiceCategory getById(int id) {
        return Arrays.stream(ServiceCategory.values())
                .filter(serviceCategory -> serviceCategory.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown id: " + id));
    }
}
