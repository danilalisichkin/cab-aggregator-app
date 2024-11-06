package com.cabaggregator.rideservice.core.dto.ride.rate;

import org.bson.types.ObjectId;

public record RideRateDto(
        ObjectId id,
        ObjectId rideId,
        Integer passengerRate,
        Integer driverRate
) {
}
