package com.example.pricecalculationservice.util;

import com.example.pricecalculationservice.entity.RideFare;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideFareTestUtil {
    public static final Integer ID = 1;
    public static final String FARE_NAME = "COMFORT";
    public static final Double BASE_PRICE_PER_KILOMETER = 5.0;
    public static final Double BASE_PRICE_PER_MINUTE = 3.0;

    public static RideFare.RideFareBuilder getRideFareBuilder() {
        return RideFare.builder()
                .id(ID)
                .fareName(FARE_NAME)
                .basePricePerKilometer(BASE_PRICE_PER_KILOMETER)
                .basePricePerMinute(BASE_PRICE_PER_MINUTE);
    }
}
