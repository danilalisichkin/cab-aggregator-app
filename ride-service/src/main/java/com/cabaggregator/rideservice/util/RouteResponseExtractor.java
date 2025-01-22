package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.client.dto.RouteSummary;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.cabaggregator.rideservice.client.constant.OpenRouteConstants.DISTANCE;
import static com.cabaggregator.rideservice.client.constant.OpenRouteConstants.DURATION;
import static com.cabaggregator.rideservice.client.constant.OpenRouteConstants.ROUTES;
import static com.cabaggregator.rideservice.client.constant.OpenRouteConstants.SUMMARY;

/**
 * Extracts route summary from OpenRouteAPI response.
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RouteResponseExtractor {
    @SuppressWarnings("unchecked")
    public static RouteSummary extractRouteSummary(Map<String, Object> openRouteApiResponse) {
        List<Map<String, Object>> routes = (List<Map<String, Object>>) openRouteApiResponse.get(ROUTES);
        Map<String, Object> summary = (Map<String, Object>) routes.getFirst().get(SUMMARY);

        Long distance = ((Double) summary.get(DISTANCE)).longValue();
        Long duration = ((Double) summary.get(DURATION)).longValue();

        return new RouteSummary(distance, duration);
    }
}
