package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.client.dto.PriceCalculationRequest;
import com.cabaggregator.rideservice.client.dto.PriceResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PriceTestUtil {
    public static final Long PRICE = 1200L;
    public static final String DEMAND = "STANDARD";
    public static final String WEATHER = "Overcast";

    public static PriceCalculationRequest buildPriceCalculationRequest() {
        return new PriceCalculationRequest(
                RideTestUtil.ID,
                RideTestUtil.PICK_UP_COORDINATES,
                RouteTestUtil.DISTANCE,
                RouteTestUtil.ESTIMATED_DURATION,
                RideTestUtil.FARE);
    }

    public static PriceResponse buildPriceResponse() {
        return new PriceResponse(
                PRICE,
                DEMAND,
                WEATHER);
    }
}
