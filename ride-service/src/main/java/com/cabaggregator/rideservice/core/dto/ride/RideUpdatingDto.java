package com.cabaggregator.rideservice.core.dto.ride;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RideUpdatingDto(
        @NotNull
        PaymentMethod paymentMethod,

        @Valid
        Address pickUpAddress,

        @Valid
        Address dropOffAddress
) {
}
