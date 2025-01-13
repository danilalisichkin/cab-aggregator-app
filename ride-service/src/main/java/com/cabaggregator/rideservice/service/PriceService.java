package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.core.dto.price.PriceRecalculationDto;

public interface PriceService {
    Long calculateBasePrice(PriceCalculationRequest priceCalculationRequest);

    Long recalculatePriceWithDiscount(PriceRecalculationDto recalculationDto);
}
