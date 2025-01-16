package com.cabaggregator.promocodeservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IntegrationTestUtil {
    public static final String LOCAL_HOST = "http://localhost";
    public static final String PROMO_CODES_BASE_URL = "api/v1/promo-codes";
    public static final String PROMO_STATS_BASE_URL = "api/v1/promo-stats";
}
