package com.cabaggregator.rideservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceCategory {
    ECONOM(1, "ECONOM"),
    COMFORT(2, "COMFORT"),
    BUSINESS(3, "BUSINESS");

    private final int id;
    private final String value;

    public static ServiceCategory getById(int id) {
        for (ServiceCategory serviceCategory : ServiceCategory.values()) {
            if (serviceCategory.getId() == id)
                return serviceCategory;
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}
