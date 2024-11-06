package com.cabaggregator.rideservice.core.dto.ride;

import java.time.LocalDateTime;

public record RideUpdatingDto(
        Long passengerId,
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
