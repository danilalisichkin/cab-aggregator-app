package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.client.OpenRouteApiClient;
import com.cabaggregator.rideservice.client.dto.RouteRequest;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.route.RouteSummary;
import com.cabaggregator.rideservice.entity.Ride;
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
     * Set route summary for ride: its distance and estimated duration.
     * Uses synchronized calls to OpenRoute
     **/
    @Override
    public void setRouteSummary(Ride ride, RideAddingDto addingDto) {
        List<List<Double>> routeCoordinates = List.of(
                addingDto.pickUpAddress().coordinates(),
                addingDto.dropOffAddress().coordinates());
        RouteRequest routeRequest = new RouteRequest(routeCoordinates);

        Map<String, Object> openRouteResponse = openRouteApiClient.getDrivingCarRoute(routeRequest);
        RouteSummary routeSummary = RouteResponseExtractor.extractRouteSummary(openRouteResponse);

        ride.setDistance(routeSummary.distance());
        ride.setEstimatedDuration(routeSummary.duration());
    }
}
