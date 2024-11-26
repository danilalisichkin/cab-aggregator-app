package com.example.pricecalculationservice.util;

import com.example.pricecalculationservice.entity.DemandCoefficient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DemandCoefficientTestUtil {
    public static final Integer ID = 1;
    public static final Integer MIN_ORDERS = 10;
    public static final Double PRICE_COEFFICIENT = 1.15;

    public static DemandCoefficient.DemandCoefficientBuilder getDemandCoefficientBuilder() {
        return DemandCoefficient.builder()
                .id(ID)
                .minOrders(MIN_ORDERS)
                .priceCoefficient(PRICE_COEFFICIENT);
    }

    public static DemandCoefficient.DemandCoefficientBuilder getLowDemandCoefficientBuilder() {
        return DemandCoefficient.builder()
                .id(2)
                .minOrders(5)
                .priceCoefficient(1.1);
    }

    public static DemandCoefficient.DemandCoefficientBuilder getHighDemandCoefficientBuilder() {
        return DemandCoefficient.builder()
                .id(3)
                .minOrders(20)
                .priceCoefficient(1.2);
    }
}
