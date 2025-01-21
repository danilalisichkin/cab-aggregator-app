package com.cabaggregator.ratingservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IntegrationTestUtil {
    public static final String LOCAL_HOST = "http://localhost";
    public static final String DRIVER_RATES_BASE_URL = "api/v1/rates/driver";
    public static final String PASSENGER_RATES_BASE_URL = "api/v1/rates/passenger";
}
