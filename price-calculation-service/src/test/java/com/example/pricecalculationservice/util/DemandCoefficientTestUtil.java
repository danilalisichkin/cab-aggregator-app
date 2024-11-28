package com.example.pricecalculationservice.util;

import com.example.pricecalculationservice.entity.DemandCoefficient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DemandCoefficientTestUtil {
    public static DemandCoefficient.DemandCoefficientBuilder getStandardDemandCoefficientBuilder() {
        return DemandCoefficient.builder()
                .demand("STANDARD")
                .minOrders(10)
                .priceCoefficient(1.0);
    }

    public static DemandCoefficient.DemandCoefficientBuilder getLowDemandCoefficientBuilder() {
        return DemandCoefficient.builder()
                .demand("LOW")
                .minOrders(0)
                .priceCoefficient(0.9);
    }

    public static DemandCoefficient.DemandCoefficientBuilder getHighDemandCoefficientBuilder() {
        return DemandCoefficient.builder()
                .demand("HIGH")
                .minOrders(20)
                .priceCoefficient(1.1);
    }
}
