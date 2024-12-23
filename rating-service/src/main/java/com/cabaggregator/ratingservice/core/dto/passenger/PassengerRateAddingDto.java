package com.cabaggregator.ratingservice.core.dto.passenger;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;

import java.util.UUID;

@Schema(description = "Entry to add new passenger rate")
public record PassengerRateAddingDto(
        @NotNull
        ObjectId rideId,

        @NotNull
        UUID passengerId,

        @NotNull
        UUID driverId
) {
}
