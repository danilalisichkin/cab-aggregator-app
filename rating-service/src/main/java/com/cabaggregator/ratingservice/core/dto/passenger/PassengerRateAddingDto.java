package com.cabaggregator.ratingservice.core.dto.passenger;

import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.util.UUID;

public record PassengerRateAddingDto(
        @NotNull
        ObjectId rideId,

        @NotNull
        UUID passengerId,

        @NotNull
        UUID driverId
) {
}
