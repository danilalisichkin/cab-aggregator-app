package com.cabaggregator.rideservice.client;

import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.client.dto.PriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "pricingApiClient",
        url = "http://localhost:8087/api/v1/pricing")
public interface PriceCalculationApiClient {
    @PostMapping
    PriceResponse calculatePrice(@RequestBody PriceCalculationRequest priceCalculationRequest);
}
