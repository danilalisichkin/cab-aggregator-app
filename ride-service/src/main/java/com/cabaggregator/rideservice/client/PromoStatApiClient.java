package com.cabaggregator.rideservice.client;

import com.cabaggregator.rideservice.client.dto.PromoStatAddingDto;
import com.cabaggregator.rideservice.client.dto.PromoStatDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "${services.promo-stat.name}",
        contextId = "promoStatClient",
        path = "${services.promo-stat.path}")
public interface PromoStatApiClient {
    @PostMapping
    PromoStatDto createPromoStat(@RequestBody PromoStatAddingDto addingDto);
}
