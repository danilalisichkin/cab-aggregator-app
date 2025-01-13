package com.cabaggregator.pricecalculationservice.util;

import com.cabaggregator.pricecalculationservice.entity.RideFare;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideFareTestUtil {
    public static final String NAME = "COMFORT";
    public static final Long PRICE_PER_KILOMETER = 130L;
    public static final Long PRICE_PER_MINUTE = 100L;

    public static RideFare buildDefaultRideFare() {
        return RideFare.builder()
                .name(NAME)
                .pricePerKilometer(PRICE_PER_KILOMETER)
                .pricePerMinute(PRICE_PER_MINUTE)
                .build();
    }
}
