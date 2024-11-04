package com.cabaggregator.rideservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CREDIT_CARD(1, "CREDIT_CARD"),
    CASH(2, "CASH");

    private final int id;
    private final String value;

    public static PaymentMethod getById(int id) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.getId() == id)
                return paymentMethod;
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}

