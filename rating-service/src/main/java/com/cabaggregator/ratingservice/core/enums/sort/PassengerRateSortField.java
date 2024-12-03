package com.cabaggregator.ratingservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PassengerRateSortField {
    ID("id"),
    RIDE_ID("rideId"),
    PASSENGER_ID("passengerId"),
    DRIVER_ID("driverId"),
    RATE("rate");

    private final String value;
}
