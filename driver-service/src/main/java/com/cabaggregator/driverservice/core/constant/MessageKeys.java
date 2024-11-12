package com.cabaggregator.driverservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageKeys {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final class ErrorCauses {
        public static final String BAD_REQUEST = "error.cause.bad.request";
        public static final String NOT_FOUND = "error.cause.not.found";
        public static final String UNIQUENESS_CONFLICT = "error.cause.uniqueness.conflict";
        public static final String VALIDATION = "error.cause.validation";
        public static final String INTERNAL = "error.cause.internal";
        public static final String CANT_READ_REQUEST="error.cant.read.request";
        public static final String CONTACT_DEVELOPERS="error.if.persists.contact.developers";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final class ValidationErrors {
        public static final String INVALID_SORT_ORDER  = "{validation.invalid.sort.order}";
        public static final String INVALID_PHONE_FORMAT = "{validation.invalid.phone.format}";
        public static final String INVALID_LICENCE_PLATE_FORMAT = "{validation.invalid.licence.plate.format}";
        public static final String INVALID_LENGTH = "{validation.invalid.length}";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final class ApplicationMessages {
        public static final String DRIVER_WITH_ID_NOT_FOUND = "error.driver.with.id.not.found";
        public static final String DRIVER_WITH_PHONE_ALREADY_EXISTS = "error.driver.with.phone.already.exists";
        public static final String DRIVER_WITH_EMAIL_ALREADY_EXISTS="error.driver.with.email.already.exists";
        public static final String CAR_WITH_ID_NOT_FOUND="error.car.with.id.not.found";
        public static final String CAR_WITH_LICENCE_PLATE_ALREADY_EXISTS="error.car.with.license.plate.already.exists";
        public static final String CAR_DETAILS_WITH_CAR_ID_ALREADY_EXISTS="error.car.details.with.car.id.already.exists";
        public static final String CAR_RELEASE_DATE_IS_AFTER_PRESENT="error.car.release.date.is.after.present";
    }
}
