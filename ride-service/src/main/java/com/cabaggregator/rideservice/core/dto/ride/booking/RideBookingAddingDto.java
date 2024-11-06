package com.cabaggregator.rideservice.core.dto.ride.booking;

public record RideBookingAddingDto(
        Long passengerId,
        String serviceCategory,
        String paymentMethod,
        String pickupAddress,
        String destinationAddress
) {
}
