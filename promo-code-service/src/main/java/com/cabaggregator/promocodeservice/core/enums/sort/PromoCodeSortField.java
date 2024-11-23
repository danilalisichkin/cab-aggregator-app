package com.cabaggregator.promocodeservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromoCodeSortField {
    VALUE("value"),
    DISCOUNT("discountPercentage"),
    END_DATE("endDate"),
    LIMITS("limits");

    private final String value;
}
