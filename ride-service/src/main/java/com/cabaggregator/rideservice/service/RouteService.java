package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.client.dto.RouteSummary;

import java.util.List;

public interface RouteService {
    RouteSummary getRouteSummary(List<List<Double>> routeCoordinates);
}
