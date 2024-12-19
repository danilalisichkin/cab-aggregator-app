package com.cabaggregator.ratingservice.core.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.util.UUID;

@Schema(description = "Entry to add new driver rate")
public record DriverRateAddingDto(
        @NotNull
        ObjectId rideId,

        @NotNull
        UUID driverId,

        @NotNull
        UUID passengerId
) {
}
