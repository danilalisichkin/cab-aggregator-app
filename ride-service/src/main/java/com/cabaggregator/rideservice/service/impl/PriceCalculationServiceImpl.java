package com.cabaggregator.rideservice.service.impl;

import com.cabaggregator.rideservice.entity.enums.ServiceCategory;
import com.cabaggregator.rideservice.service.PriceCalculationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceCalculationServiceImpl implements PriceCalculationService {
    @Override
    public BigDecimal calculatePrice(String pickupAddress, String destinationAddress, ServiceCategory category) {
        // TODO: implement using Google Cloud maps API

        return null;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal price, Integer discount) {
        BigDecimal discountAmount = price
                .multiply(BigDecimal.valueOf(discount))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

        return price.subtract(discountAmount);
    }
}
