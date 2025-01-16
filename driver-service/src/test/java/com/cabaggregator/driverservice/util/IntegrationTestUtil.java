package com.cabaggregator.driverservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IntegrationTestUtil {
    public static final String LOCAL_HOST = "http://localhost";
    public static final String DRIVERS_BASE_URL = "api/v1/drivers";
    public static final String CARS_BASE_URL = "/api/v1/cars";
}
