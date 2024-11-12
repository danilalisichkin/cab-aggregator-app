package com.cabaggregator.rideservice.core.dto.ride.booking;

import com.cabaggregator.rideservice.entity.enums.PaymentMethod;
import com.cabaggregator.rideservice.entity.enums.ServiceCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RideBookingAddingDto(
        @NotNull
        Long passengerId,

        @NotNull
        ServiceCategory serviceCategory,

        @NotNull
        PaymentMethod paymentMethod,

        @NotNull
        @Size(min = 3, max = 100)
        String pickupAddress,

        @NotNull
        @Size(min = 3, max = 100)
        String destinationAddress
) {
}
