package com.cabaggregator.pricecalculationservice.util;

import com.cabaggregator.pricecalculationservice.entity.WeatherCoefficient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeatherCoefficientTestUtil {
    public static final String WEATHER = "Heavy rain";
    public static final Double PRICE_COEFFICIENT = 1.20;

    public static WeatherCoefficient.WeatherCoefficientBuilder getWeatherCoefficientBuilder() {
        return WeatherCoefficient.builder()
                .weather(WEATHER)
                .priceCoefficient(PRICE_COEFFICIENT);
    }
}
