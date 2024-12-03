package com.cabaggregator.ratingservice.core.dto.driver;

import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.util.UUID;

public record DriverRateAddingDto(
        @NotNull
        ObjectId rideId,

        @NotNull
        UUID driverId,

        @NotNull
        UUID passengerId
) {
}
