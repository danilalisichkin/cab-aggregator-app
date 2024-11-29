package com.cabaggregator.ratingservice.core.dto.passenger;

import org.bson.types.ObjectId;

import java.util.UUID;

public record PassengerRateDto(
        ObjectId id,
        ObjectId rideId,
        UUID passengerId,
        UUID driverId,
        Integer rate
) {
}
