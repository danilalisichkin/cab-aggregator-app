package com.cabaggregator.rideservice.core;

import com.cabaggregator.rideservice.core.dto.Address;
import com.cabaggregator.rideservice.core.enums.PaymentMethod;
import com.cabaggregator.rideservice.core.enums.RideFare;

public record RideAddingDto(
        RideFare fare,
        PaymentMethod paymentMethod,
        Address pickUpAddress,
        Address dropOffAddress
) {
}
