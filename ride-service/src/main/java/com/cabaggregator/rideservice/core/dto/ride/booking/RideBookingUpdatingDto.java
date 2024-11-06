package com.cabaggregator.rideservice.core.dto.ride.booking;

public record RideBookingUpdatingDto(
        Long passengerId,
        String paymentMethod,
        String pickupAddress,
        String destinationAddress
) {
}
