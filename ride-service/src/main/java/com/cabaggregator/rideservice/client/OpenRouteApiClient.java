package com.cabaggregator.rideservice.client;

import com.cabaggregator.rideservice.client.config.OpenRouteApiClientInterceptor;
import com.cabaggregator.rideservice.client.dto.RouteRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "openRouteApiClient",
        url = "https://api.openrouteservice.org/v2",
        configuration = OpenRouteApiClientInterceptor.class)
public interface OpenRouteApiClient {
    @PostMapping("/directions/driving-car/json")
    Map<String, Object> getDrivingCarRoute(@RequestBody RouteRequest routeRequest);
}
