package com.cabaggregator.passengerservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IntegrationTestUtil {
    public static final String LOCAL_HOST = "http://localhost";
    public static final String PASSENGERS_BASE_URL = "api/v1/passengers";
}
