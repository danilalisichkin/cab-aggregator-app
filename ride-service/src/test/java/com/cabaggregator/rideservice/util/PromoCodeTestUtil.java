package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.client.dto.PromoCodeDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PromoCodeTestUtil {
    public static final String VALUE = "PROMO2025";
    public static final Integer DISCOUNT_PERCENTAGE = 5;
    public static final LocalDate END_DATE = LocalDate.of(2026, 1, 1);
    public static final Long LIMIT = 1000L;

    public static PromoCodeDto buildPromoCodeDto() {
        return new PromoCodeDto(
                VALUE,
                DISCOUNT_PERCENTAGE,
                END_DATE,
                LIMIT);
    }
}
