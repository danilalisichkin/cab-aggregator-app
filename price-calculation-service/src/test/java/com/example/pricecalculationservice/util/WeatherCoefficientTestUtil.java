package com.example.pricecalculationservice.util;

import com.example.pricecalculationservice.entity.WeatherCoefficient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeatherCoefficientTestUtil {
    public static final String WEATHER = "RAIN";
    public static final Double PRICE_COEFFICIENT = 1.3;

    public static WeatherCoefficient.WeatherCoefficientBuilder getWeatherCoefficientBuilder() {
        return WeatherCoefficient.builder()
                .weather(WEATHER)
                .priceCoefficient(PRICE_COEFFICIENT);
    }
}
