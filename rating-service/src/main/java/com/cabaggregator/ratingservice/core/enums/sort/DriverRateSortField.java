package com.cabaggregator.ratingservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DriverRateSortField {
    ID("id"),
    RIDE_ID("rideId"),
    DRIVER_ID("driverId"),
    PASSENGER_ID("passengerId"),
    RATE("rate");

    private final String value;
}
