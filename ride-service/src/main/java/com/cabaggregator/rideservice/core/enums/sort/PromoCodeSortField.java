package com.cabaggregator.rideservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromoCodeSortField {
    ID("id"),
    VALUE("value"),
    DISCOUNT("discount"),
    START_DATE("startDate"),
    END_DATE("endDate");

    private final String value;
}
