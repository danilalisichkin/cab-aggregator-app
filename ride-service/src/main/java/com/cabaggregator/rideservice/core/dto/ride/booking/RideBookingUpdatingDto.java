package com.cabaggregator.rideservice.core.dto.ride.booking;

public record RideBookingUpdatingDto(
        long passengerId,
        String promoCode,
        String paymentMethod,
        String pickupAddress,
        String destinationAddress
) {
}
