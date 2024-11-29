package com.cabaggregator.ratingservice.core.dto.driver;

import org.bson.types.ObjectId;

import java.util.UUID;

public record DriverRateAddingDto(
        ObjectId rideId,
        UUID driverId,
        UUID passengerId
) {
}
