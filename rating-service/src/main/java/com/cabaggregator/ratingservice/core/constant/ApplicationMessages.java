package com.cabaggregator.ratingservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String RIDE_RATE_WITH_RIDE_ID_NOT_FOUND = "error.ride.rate.with.ride.id.not.found";
    public static final String RIDE_RATE_WITH_RIDE_ID_ALREADY_EXISTS = "error.ride.rate.with.ride.id.already.exists";
    public static final String RIDE_RATE_ALREADY_SET = "error.ride.rate.already.set";
    public static final String USER_NOT_RIDE_PARTICIPANT = "error.user.not.ride.participant";
    public static final String CANT_GET_RATES_OF_OTHER_USER = "error.cant.get.rates.of.other.user";
}
