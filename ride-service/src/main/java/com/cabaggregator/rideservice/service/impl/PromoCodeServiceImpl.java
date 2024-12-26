package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.client.PromoCodeApiClient;
import com.cabaggregator.rideservice.client.PromoStatApiClient;
import com.cabaggregator.rideservice.client.dto.PromoCodeDto;
import com.cabaggregator.rideservice.client.dto.PromoStatAddingDto;
import com.cabaggregator.rideservice.service.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {
    private final PromoStatApiClient promoStatApiClient;

    private final PromoCodeApiClient promoCodeApiClient;

    /**
     * Fetches promo code.
     **/
    @Override
    public PromoCodeDto getPromoCode(String promoCode) {
        return promoCodeApiClient.getPromoCode(promoCode);
    }

    /**
     * Creates promo code stat to redeem it.
     **/
    @Override
    public void createPromoStat(UUID userId, String promoCode) {
        PromoStatAddingDto promoStatAddingDto = new PromoStatAddingDto(userId, promoCode);
        promoStatApiClient.createPromoStat(promoStatAddingDto);
    }
}
