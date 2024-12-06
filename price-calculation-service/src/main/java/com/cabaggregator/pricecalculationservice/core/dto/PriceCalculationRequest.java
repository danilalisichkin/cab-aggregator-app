package com.cabaggregator.pricecalculationservice.core.dto;

public record PriceCalculationRequest(
        Long distance,
        Long duration,
        String fare
) {
}
