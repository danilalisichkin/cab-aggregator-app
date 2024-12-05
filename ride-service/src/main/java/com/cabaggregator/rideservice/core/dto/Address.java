package com.cabaggregator.rideservice.core.dto;

import java.util.List;

public record Address(
        String fullAddress,
        List<Double> coordinates
) {
}
