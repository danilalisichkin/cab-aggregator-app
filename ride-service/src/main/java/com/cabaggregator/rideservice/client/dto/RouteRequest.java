package com.cabaggregator.rideservice.client.dto;

import java.util.List;

public record RouteRequest(
        List<List<Double>> coordinates
) {
}
