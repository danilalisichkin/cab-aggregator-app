package com.cabaggregator.rideservice.core.dto.ride;

import java.time.LocalDateTime;

public record RideUpdatingDto(
        long passengerId,
        String promoCode,
        String status,
        String serviceCategory,
        String paymentMethod,
        String pickupAddress,
        String destinationAddress,
        double cost,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}