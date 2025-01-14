package com.cabaggregator.rideservice.client;

import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.client.dto.PriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        value = "${services.price.name}",
        path = "${services.price.path}")
public interface PriceCalculationApiClient {
    @PostMapping
    PriceResponse calculatePrice(@RequestBody PriceCalculationRequest priceCalculationRequest);
}
