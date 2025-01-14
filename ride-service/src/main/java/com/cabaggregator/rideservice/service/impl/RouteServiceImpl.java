package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.client.OpenRouteApiClient;
import com.cabaggregator.rideservice.client.dto.RouteRequest;
import com.cabaggregator.rideservice.core.dto.route.RouteSummary;
import com.cabaggregator.rideservice.service.RouteService;
import com.cabaggregator.rideservice.util.RouteResponseExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final OpenRouteApiClient openRouteApiClient;

    /**
     * Gets route summary for ride: its distance and estimated duration.
     * Uses synchronized calls to OpenRoute API.
     **/
    @Override
    public RouteSummary getRouteSummary(List<List<Double>> routeCoordinates) {
        RouteRequest routeRequest = new RouteRequest(routeCoordinates);

        Map<String, Object> openRouteResponse = openRouteApiClient.getDrivingCarRoute(routeRequest);

        return RouteResponseExtractor.extractRouteSummary(openRouteResponse);
    }
}
