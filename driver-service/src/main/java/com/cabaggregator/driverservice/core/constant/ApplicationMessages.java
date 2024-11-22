package com.cabaggregator.driverservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String DRIVER_WITH_ID_NOT_FOUND = "error.driver.with.id.not.found";
    public static final String DRIVER_WITH_ID_ALREADY_EXISTS = "error.driver.with.id.already.exists";
    public static final String DRIVER_WITH_PHONE_ALREADY_EXISTS = "error.driver.with.phone.already.exists";
    public static final String DRIVER_WITH_EMAIL_ALREADY_EXISTS = "error.driver.with.email.already.exists";
    public static final String CAR_WITH_ID_NOT_FOUND = "error.car.with.id.not.found";
    public static final String CAR_WITH_ID_ALREADY_USED = "error.car.with.id.already.used";
    public static final String CAR_WITH_LICENCE_PLATE_ALREADY_EXISTS = "error.car.with.license.plate.already.exists";
    public static final String CAR_DETAILS_WITH_ID_NOT_FOUND = "error.car.details.with.id.not.found";
    public static final String CAR_RELEASE_DATE_IS_AFTER_PRESENT = "error.car.release.date.is.after.present";
}
