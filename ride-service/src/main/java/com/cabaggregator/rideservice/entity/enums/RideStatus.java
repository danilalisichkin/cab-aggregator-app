package com.cabaggregator.rideservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RideStatus {
    PREPARED(1, "PREPARED"),
    REQUESTED(2, "REQUESTED"),
    ARRIVING(3, "ARRIVING"),
    WAITING(4, "WAITING"),
    IN_PROGRESS(5, "IN_PROGRESS"),
    COMPLETED(6, "COMPLETED"),
    CANCELED(7, "CANCELED");

    private final int id;
    private final String value;

    public static RideStatus getById(int id) {
        return Arrays.stream(RideStatus.values())
                .filter(rideStatus -> rideStatus.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown id: " + id));
    }
}
