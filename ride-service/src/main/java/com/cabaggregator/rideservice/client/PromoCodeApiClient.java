package com.cabaggregator.rideservice.client;

import com.cabaggregator.rideservice.client.dto.PromoCodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "promoCodeApiClient",
        url = "http://localhost:8083/api/v1/promo-codes")
public interface PromoCodeApiClient {
    @GetMapping("/{code}")
    PromoCodeDto getPromoCode(@PathVariable String code);
}
