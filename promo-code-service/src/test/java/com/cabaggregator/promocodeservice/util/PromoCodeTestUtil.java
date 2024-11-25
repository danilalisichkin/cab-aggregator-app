package com.cabaggregator.promocodeservice.util;

import com.cabaggregator.promocodeservice.entity.PromoCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PromoCodeTestUtil {
    public static final String VALUE = "PROMO2024";
    public static final Integer DISCOUNT_PERCENTAGE = 5;
    public static final LocalDateTime END_DATE = LocalDateTime.of(2024, 12, 31, 23, 59);
    public static final Long LIMITS = 1000L;

    public static PromoCode buildPromoCode() {
        return PromoCode.builder()
                .value(VALUE)
                .discountPercentage(DISCOUNT_PERCENTAGE)
                .endDate(END_DATE)
                .limits(LIMITS)
                .build();
    }
}

