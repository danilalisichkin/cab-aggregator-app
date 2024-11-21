package com.cabaggregator.passengerservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String ERROR_PASSENGER_WITH_ID_NOT_FOUND = "error.passenger.with.id.not.found";
    public static final String PASSENGER_WITH_ID_ALREADY_EXISTS = "error.passenger.with.id.already.exists";
    public static final String ERROR_PASSENGER_WITH_PHONE_ALREADY_EXISTS = "error.passenger.with.phone.already.exists";
    public static final String ERROR_PASSENGER_WITH_EMAIL_ALREADY_EXISTS = "error.passenger.with.email.already.exists";
}
