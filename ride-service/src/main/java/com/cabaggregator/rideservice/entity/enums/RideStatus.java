package com.cabaggregator.rideservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RideStatus {
    REQUESTED(1, "REQUESTED"),
    ARRIVING(2, "ARRIVING"),
    WAITING(3, "WAITING"),
    IN_PROGRESS(4, "IN_PROGRESS"),
    COMPLETED(5, "COMPLETED"),
    CANCELED(6, "CANCELED");

    private final int id;
    private final String value;

    public static RideStatus getById(int id) {
        for (RideStatus rideStatus : RideStatus.values()) {
            if (rideStatus.getId() == id)
                return rideStatus;
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}
