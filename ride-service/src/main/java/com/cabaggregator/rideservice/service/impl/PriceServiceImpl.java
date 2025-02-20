package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.client.PriceCalculationApiClient;
import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.client.dto.PriceRecalculationDto;
import com.cabaggregator.rideservice.client.dto.PriceResponse;
import com.cabaggregator.rideservice.client.dto.PromoCodeDto;
import com.cabaggregator.rideservice.service.PriceService;
import com.cabaggregator.rideservice.service.PromoCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceCalculationApiClient priceCalculationApiClient;

    private final PromoCodeService promoCodeService;

    /**
     * Calculates base price of ride depending on fare, demand, weather, duration and distance.
     * Uses synchronized Price Calculation Service API calls.
     **/
    @Override
    public Long calculateBasePrice(PriceCalculationRequest priceCalculationRequest) {
        PriceResponse priceResponse = priceCalculationApiClient.calculatePrice(priceCalculationRequest);

        log.info("Calculated price={} for ride with id={}", priceResponse.getPrice(), priceCalculationRequest.rideId());

        return priceResponse.getPrice();
    }

    /**
     * Recalculates price of ride by applying promo code to it.
     * Uses synchronized Promo Code Service API calls.
     * Firstly fetching Promo Code to get its discount percentage,
     * then creates promo stat record to redeem promo code and recalculates price by subtraction.
     **/
    @Override
    public Long recalculatePriceWithDiscount(PriceRecalculationDto recalculationDto) {
        PromoCodeDto promoCodeDto = promoCodeService.getPromoCode(recalculationDto.promoCode());
        promoCodeService.createPromoStat(recalculationDto.passengerId(), promoCodeDto.value());

        Long originalPrice = recalculationDto.price();
        Integer discountPercentage = promoCodeDto.discountPercentage();
        Double discountAmount = originalPrice * discountPercentage / 100.0;

        Long recalculatedPrice = originalPrice - discountAmount.longValue();

        log.info("Recalculated price by decreasing it from {} to {}", originalPrice, recalculatedPrice);

        return recalculatedPrice;
    }
}
