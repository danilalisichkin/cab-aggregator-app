package com.cabaggregator.rideservice.core.dto.ride;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Entry to update existing ride")
public record RideUpdatingDto(
        @NotNull
        PaymentMethod paymentMethod,

        @Valid
        Address pickUpAddress,

        @Valid
        Address dropOffAddress
) {
}
