package com.cabaggregator.rideservice.core.dto.ride.booking;

public record RideBookingAddingDto(
        long passengerId,
        String promoCode,
        String serviceCategory,
        String paymentMethod,
        String pickupAddress,
        String destinationAddress
) {
}
