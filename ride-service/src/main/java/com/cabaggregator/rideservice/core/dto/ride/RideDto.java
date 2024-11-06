package com.cabaggregator.rideservice.core.dto.ride;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public record RideDto(
        ObjectId rideId,
        Long passengerId,
        Long driverId,
        String promoCode,
        String serviceCategory,
        String status,
        String paymentMethod,
        String pickupAddress,
        String destinationAddress,
        Double cost,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
