package com.cabaggregator.rideservice.core.dto.ride;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.RideFare;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record RideAddingDto(
        @NotNull
        RideFare fare,

        @NotNull
        PaymentMethod paymentMethod,

        @Valid
        Address pickUpAddress,

        @Valid
        Address dropOffAddress
) {
}
