package com.cabaggregator.rideservice.client;

import com.cabaggregator.rideservice.client.dto.PromoStatAddingDto;
import com.cabaggregator.rideservice.client.dto.PromoStatDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "promoStatApiClient",
        url = "http://localhost:8083/api/v1/promo-stats")
public interface PromoStatApiClient {
    @PostMapping
    PromoStatDto createPromoStat(@RequestBody PromoStatAddingDto addingDto);
}
