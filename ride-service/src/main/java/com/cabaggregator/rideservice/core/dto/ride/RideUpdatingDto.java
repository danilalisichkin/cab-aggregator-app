package com.cabaggregator.rideservice.core.dto.ride;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public record RideUpdatingDto(
        Long passengerId,
        String promoCode,
        String serviceCategory,
        String status,
        String paymentMethod,
        String pickupAddress,
        String destinationAddress,
        BigDecimal cost,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
    public BigDecimal cost() {
        return cost.setScale(2, RoundingMode.HALF_UP);
    }
}
