package com.cabaggregator.rideservice.service;

import com.cabaggregator.rideservice.entity.enums.ServiceCategory;

import java.math.BigDecimal;

public interface PriceCalculationService {
    BigDecimal calculatePrice(String pickupAddress, String destinationAddress, ServiceCategory category);

    BigDecimal applyDiscount(BigDecimal price, Integer discount);
}
