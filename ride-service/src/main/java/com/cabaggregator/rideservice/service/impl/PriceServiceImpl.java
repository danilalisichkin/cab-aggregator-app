package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.client.PriceCalculationApiClient;
import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.client.dto.PriceResponse;
import com.cabaggregator.rideservice.client.dto.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.service.PriceService;
import com.cabaggregator.rideservice.service.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceCalculationApiClient priceCalculationApiClient;

    private final PromoCodeService promoCodeService;

    /**
     * Calculates base price of ride depending on fare, demand, weather, duration and distance.
     * Uses synchronized Price Calculation Service API calls.
     **/
    @Override
    public void calculateBasePrice(Ride ride, RideAddingDto addingDto) {
        PriceCalculationRequest priceCalculationRequest = new PriceCalculationRequest(
                ride.getId(),
                addingDto.pickUpAddress().coordinates(),
                ride.getDistance(),
                ride.getEstimatedDuration(),
                addingDto.fare());

        PriceResponse priceResponse = priceCalculationApiClient.calculatePrice(priceCalculationRequest);

        ride.setPrice(priceResponse.getPrice());
    }

    /**
     * Recalculates price of ride by applying promo code to it.
     * Uses synchronized Promo Code Service API calls.
     * Firstly fetching Promo Code to get its discount percentage,
     * then creates promo stat record to redeem promo code and recalculates price by subtraction.
     **/
    @Override
    public void recalculatePriceWithDiscount(Ride ride, RideAddingDto addingDto) {
        PromoCodeDto promoCodeDto = promoCodeService.getPromoCode(addingDto.promoCode());
        promoCodeService.createPromoStat(ride.getPassengerId(), promoCodeDto.value());

        Long priceWithDiscount = ride.getPrice() - ride.getPrice() * promoCodeDto.discountPercentage() / 100;
        ride.setPromoCode(promoCodeDto.value());
        ride.setPrice(priceWithDiscount);
    }
}
