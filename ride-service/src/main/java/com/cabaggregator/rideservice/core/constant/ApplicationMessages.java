package com.cabaggregator.rideservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String RIDE_WITH_ID_NOT_FOUND = "error.ride.with.id.not.found";
    public static final String PICKUP_AND_DROPOFF_ADDRESSES_SAME = "error.pickup.and.dropoff.addresses.same";
    public static final String USER_NOT_RIDE_PARTICIPANT = "error.user.not.ride.participant";
    public static final String CANT_GET_RIDES_OF_OTHER_USER = "error.cant.get.rides.of.other.user";
    public static final String CANT_CHANGE_RIDE_WHEN_IT_REQUESTED = "error.cant.change.ride.when.it.requested";
    public static final String CANT_CHANGE_PAYMENT_STATUS_WHEN_PAID_WITH_CARD =
            "error.cant.change.payment.status.when.paid.with.cash";
    public static final String CANT_COMPLETE_RIDE_WHEN_IT_NOT_PAID = "error.cant.complete.ride.when.it.not.paid";
    public static final String STATUS_CHANGING_NOT_ALLOWED = "error.changing.ride.status.not.allowed";
    public static final String USER_PARTICIPATING_IN_ANOTHER_RIDE_NOW = "error.user.participating.in.another.ride.now";
}
