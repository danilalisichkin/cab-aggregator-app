package com.cabaggregator.ratingservice.core.dto.driver;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.util.UUID;

public record DriverRateAddingDto(
        @NotNull
        @Min(1)
        @Max(5)
        ObjectId rideId,

        @NotNull
        UUID driverId,

        @NotNull
        UUID passengerId
) {
}
