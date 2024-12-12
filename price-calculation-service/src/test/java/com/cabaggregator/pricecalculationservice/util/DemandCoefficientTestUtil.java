package com.cabaggregator.pricecalculationservice.util;

import com.cabaggregator.pricecalculationservice.entity.DemandCoefficient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DemandCoefficientTestUtil {

    public static DemandCoefficient buildLowDemandCoefficient() {
        return DemandCoefficient.builder()
                .demand("LOW")
                .minOrders(0)
                .priceCoefficient(0.90)
                .build();
    }

    public static DemandCoefficient buildStandardDemandCoefficient() {
        return DemandCoefficient.builder()
                .demand("STANDARD")
                .minOrders(5)
                .priceCoefficient(1.00)
                .build();
    }

    public static DemandCoefficient buildMediumDemandCoefficient() {
        return DemandCoefficient.builder()
                .demand("MEDIUM")
                .minOrders(10)
                .priceCoefficient(1.15)
                .build();
    }

    public static DemandCoefficient buildHighDemandCoefficient() {
        return DemandCoefficient.builder()
                .demand("HIGH")
                .minOrders(15)
                .priceCoefficient(1.30)
                .build();
    }
}
