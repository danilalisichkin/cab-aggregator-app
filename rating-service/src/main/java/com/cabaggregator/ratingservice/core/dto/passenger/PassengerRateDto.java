package com.cabaggregator.ratingservice.core.dto.passenger;

import io.swagger.v3.oas.annotations.media.Schema;
import org.bson.types.ObjectId;

import java.util.UUID;

@Schema(description = "Server response passenger rate data")
public record PassengerRateDto(
        ObjectId id,
        ObjectId rideId,
        UUID passengerId,
        UUID driverId,
        Integer rate
) {
}
