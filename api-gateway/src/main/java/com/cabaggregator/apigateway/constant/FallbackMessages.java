package com.cabaggregator.apigateway.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FallbackMessages {
    public static final String USER_FALLBACK_RESPONSE = "User service is unavailable now. Please try later.";
    public static final String AUTH_FALLBACK_RESPONSE = "Auth service is unavailable now. Please try later.";
    public static final String PASSENGER_FALLBACK_RESPONSE = "Passenger service is unavailable now. Please try later.";
    public static final String DRIVER_FALLBACK_RESPONSE = "Driver service is unavailable now. Please try later.";
    public static final String CAR_FALLBACK_RESPONSE = "Car service is unavailable now. Please try later.";
    public static final String RIDE_FALLBACK_RESPONSE = "Ride service is unavailable now. Please try later.";
    public static final String PROMO_CODE_FALLBACK_RESPONSE = "Promo code service is unavailable now. Please try later.";
    public static final String PROMO_STAT_FALLBACK_RESPONSE = "Promo stat service is unavailable now. Please try later.";
    public static final String RATING_FALLBACK_RESPONSE = "Rating service is unavailable now. Please try later.";
    public static final String PRICING_FALLBACK_RESPONSE = "Passenger service is unavailable now. Please try later.";
    public static final String PAYMENT_FALLBACK_RESPONSE = "Payment service is unavailable now. Please try later.";
    public static final String PAYOUT_FALLBACK_RESPONSE = "Payout service is unavailable now. Please try later.";
}
