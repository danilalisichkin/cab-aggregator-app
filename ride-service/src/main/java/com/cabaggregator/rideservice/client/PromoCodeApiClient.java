package com.cabaggregator.rideservice.client;

import com.cabaggregator.rideservice.client.dto.PromoCodeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "${services.promo-code.name}",
        path = "${services.promo-code.path}")
public interface PromoCodeApiClient {
    @GetMapping("/{code}")
    PromoCodeDto getPromoCode(@PathVariable String code);
}
