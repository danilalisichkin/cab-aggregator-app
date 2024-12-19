package com.cabaggregator.pricecalculationservice.service;

import com.cabaggregator.pricecalculationservice.core.dto.PriceCalculationRequest;
import com.cabaggregator.pricecalculationservice.core.dto.PriceResponse;

public interface PriceCalculationService {
    PriceResponse calculatePrice(PriceCalculationRequest request);
}
