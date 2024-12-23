package com.cabaggregator.payoutservice.stripe.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
    USD("usd");

    private final String value;
}
