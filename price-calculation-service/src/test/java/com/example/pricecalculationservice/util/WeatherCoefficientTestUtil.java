package com.example.pricecalculationservice.util;

import com.example.pricecalculationservice.entity.WeatherCoefficient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeatherCoefficientTestUtil {
    public static final Integer ID = 1;
    public static final String WEATHER = "RAIN";
    public static final Double PRICE_COEFFICIENT = 1.3;

    public static WeatherCoefficient buildWeatherCoefficient() {
        return WeatherCoefficient.builder()
                .id(ID)
                .weather(WEATHER)
                .priceCoefficient(PRICE_COEFFICIENT)
                .build();
    }
}
