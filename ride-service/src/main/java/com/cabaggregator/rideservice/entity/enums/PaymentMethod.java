package com.cabaggregator.rideservice.entity.enums;

import com.cabaggregator.rideservice.entity.conveter.PaymentMethodConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
@JsonSerialize(using = PaymentMethodConverter.Serializer.class)
@JsonDeserialize(using = PaymentMethodConverter.Deserializer.class)
public enum PaymentMethod {
    CREDIT_CARD(1, "CREDIT_CARD"),
    CASH(2, "CASH");

    private final int id;
    private final String value;

    public static PaymentMethod getById(int id) {
        return Arrays.stream(PaymentMethod.values())
                .filter(paymentMethod -> paymentMethod.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown id: " + id));
    }
}

