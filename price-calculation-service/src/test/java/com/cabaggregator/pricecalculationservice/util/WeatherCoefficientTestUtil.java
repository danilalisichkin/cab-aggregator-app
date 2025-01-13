package com.cabaggregator.pricecalculationservice.util;

import com.cabaggregator.pricecalculationservice.entity.WeatherCoefficient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeatherCoefficientTestUtil {
    public static final String WEATHER = "Heavy rain";
    public static final Double PRICE_COEFFICIENT = 1.20;
    public static final Map<String, Object> WEATHER_RESPONSE = new HashMap<>();

    static {
        buildWeatherResponse();
    }

    public static WeatherCoefficient buildDefaultWeatherCoefficient() {
        return WeatherCoefficient.builder()
                .weather(WEATHER)
                .priceCoefficient(PRICE_COEFFICIENT)
                .build();
    }

    private static void buildWeatherResponse() {
        Map<String, Object> condition = new HashMap<>();
        condition.put("text", "Light rain");

        Map<String, Object> current = new HashMap<>();
        current.put("condition", condition);

        WEATHER_RESPONSE.put("current", current);
    }
}
