package com.cabaggregator.rideservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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
        return Arrays.stream(RideStatus.values())
                .filter(rideStatus -> rideStatus.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown id: " + id));
    }
}
