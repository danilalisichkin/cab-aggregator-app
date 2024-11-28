package com.cabaggregator.pricecalculationservice.util;

import com.cabaggregator.pricecalculationservice.entity.RideFare;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideFareTestUtil {
    public static final String FARE_NAME = "COMFORT";
    public static final Long BASE_PRICE_PER_KILOMETER = 500L;
    public static final Long BASE_PRICE_PER_MINUTE = 300L;

    public static RideFare.RideFareBuilder getRideFareBuilder() {
        return RideFare.builder()
                .fareName(FARE_NAME)
                .basePricePerKilometer(BASE_PRICE_PER_KILOMETER)
                .basePricePerMinute(BASE_PRICE_PER_MINUTE);
    }
}
