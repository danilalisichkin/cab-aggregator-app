package com.cabaggregator.rideservice.core.dto.ride;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.RideFare;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Entry to add/create a new ride")
public record RideAddingDto(
        @NotNull
        RideFare fare,

        @NotNull
        PaymentMethod paymentMethod,

        @Valid
        Address pickUpAddress,

        @Valid
        Address dropOffAddress,

        @NotEmpty
        @Size(min = 2, max = 20, message = ValidationErrors.INVALID_STRING_LENGTH)
        String promoCode
) {
}
