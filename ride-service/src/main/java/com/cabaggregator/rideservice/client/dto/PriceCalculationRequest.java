package com.cabaggregator.rideservice.client.dto;

import com.cabaggregator.rideservice.core.enums.RideFare;
import org.bson.types.ObjectId;

import java.util.List;

public record PriceCalculationRequest(
        ObjectId rideId,
        List<Double> pickUpCoordinates,
        Long distance,
        Long duration,
        RideFare fare
) {
}
