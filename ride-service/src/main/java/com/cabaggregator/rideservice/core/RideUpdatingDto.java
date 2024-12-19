package com.cabaggregator.rideservice.core;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;

public record RideUpdatingDto(
        PaymentMethod paymentMethod,
        Address pickUpAddress,
        Address dropOffAddress
) {
}
