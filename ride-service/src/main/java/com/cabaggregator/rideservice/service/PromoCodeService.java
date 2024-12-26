package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.client.dto.PromoCodeDto;

import java.util.UUID;

public interface PromoCodeService {
    PromoCodeDto getPromoCode(String promoCode);

    void createPromoStat(UUID userId, String promoCode);
}
