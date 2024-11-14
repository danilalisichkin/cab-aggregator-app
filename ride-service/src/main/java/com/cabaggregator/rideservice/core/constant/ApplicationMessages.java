package com.cabaggregator.rideservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String PROMO_CODE_ALREADY_EXISTS = "error.promo.code.already_exists";
    public static final String PROMO_CODE_START_DATE_IN_PAST = "error.promo.code.start.date.in.past";
    public static final String PROMO_CODE_END_DATE_IN_PAST = "error.promo.code.end.date.in.past";
    public static final String PROMO_CODE_START_DATE_BEFORE_END_DATE = "error.promo.code.start.date.before.end.date";
    public static final String PROMO_CODE_NOT_FOUND = "error.promo.code.not.found";
    public static final String PROMO_CODE_EXPIRED = "error.promo.code.expired";
    public static final String PROMO_CODE_ALREADY_APPLIED = "error.promo.code.already.applied";

    public static final String RIDE_WITH_ID_NOT_FOUND = "error.ride.with.id.not.found";
    public static final String RIDE_PICKUP_AND_DESTINATION_SAME = "error.ride.pickup.and.destination.same";
    public static final String RIDE_RATE_WITH_ID_NOT_FOUND = "error.ride.rate.with.id.not.found";
    public static final String RIDE_RATE_WITH_RIDE_ID_NOT_FOUND = "error.ride.rate.with.ride.id.not.found";
    public static final String RIDE_RATE_ALREADY_SET = "error.ride.rate.already.set";
    public static final String CANT_GET_RIDES_WITH_STATUS = "error.cant.get.rides.with.status";
    public static final String CANT_CANCEL_RIDE = "error.cant.cancel.ride";
    public static final String CANT_CHANGE_RIDE_STATUS = "error.cant.change.ride.status";

    public static final String USER_MUST_HAVE_ROLE = "error.user.must.have.role";
    public static final String NO_USER_ROLE_PROVIDED = "error.no.user.role.provided";
    public static final String USER_NOT_RIDE_PARTICIPANT = "error.user.not.ride.participant";
    public static final String CANT_GET_RIDES_OF_OTHER_USER = "error.cant.get.rides.of.other.user";
}