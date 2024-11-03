package com.cabaggregator.rideservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CREDIT_CARD("CREDIT_CARD"),
    CASH("CASH");

    private final String value;
}

