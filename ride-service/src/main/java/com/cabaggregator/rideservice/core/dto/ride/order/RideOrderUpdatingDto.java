package com.cabaggregator.rideservice.core.dto.ride.order;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.entity.enums.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RideOrderUpdatingDto(
        @NotNull
        PaymentMethod paymentMethod,

        @NotEmpty
        @Size(min = 3, max = 100, message = ValidationErrors.INVALID_STRING_LENGTH)
        String pickupAddress,

        @NotEmpty
        @Size(min = 3, max = 100, message = ValidationErrors.INVALID_STRING_LENGTH)
        String destinationAddress
) {
}
