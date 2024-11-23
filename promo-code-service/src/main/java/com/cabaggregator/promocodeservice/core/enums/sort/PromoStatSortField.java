package com.cabaggregator.promocodeservice.core.enums.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromoStatSortField {
    ID("id"),
    USER_ID("userId"),
    PROMO_CODE_ID("promoCode");

    private final String value;
}
