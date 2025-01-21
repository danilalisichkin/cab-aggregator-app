package com.cabaggregator.pricecalculationservice.util;

import com.cabaggregator.pricecalculationservice.core.dto.PriceCalculationRequest;
import com.cabaggregator.pricecalculationservice.core.dto.PriceResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PriceCalculationTestUtil {
    public static final ObjectId RIDE_ID = new ObjectId("507f1f77bcf86cd799439011");
    public static final Long DISTANCE = 18556L;
    public static final Long DURATION = 1317L;
    public static final Long PRICE = 2894L;

    public static PriceCalculationRequest buildPriceCalculationRequest() {
        return new PriceCalculationRequest(
                RIDE_ID,
                GeoGridTestUtil.COORDINATES,
                DISTANCE,
                DURATION,
                RideFareTestUtil.NAME);
    }

    public static PriceResponse buildPriceResponse() {
        return new PriceResponse(
                PRICE,
                DemandCoefficientTestUtil.CURRENT_DEMAND,
                WeatherCoefficientTestUtil.WEATHER
        );
    }
}
