package com.cabaggregator.pricecalculationservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeatherResponseExtractor {
    @SuppressWarnings("unchecked")
    public static String getCurrentWeatherState(Map<String, Object> weatherApiResponse) {
        Map<String, Object> current = (Map<String, Object>) weatherApiResponse.get("current");
        Map<String, Object> condition = (Map<String, Object>) current.get("condition");

        return (String) condition.get("text");
    }
}
