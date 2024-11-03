package com.cabaggregator.driverservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract  class MessageKeys {
    public static final String ERROR_CAUSE_BAD_REQUEST = "error.cause.bad.request";
    public static final String ERROR_CAUSE_NOT_FOUND = "error.cause.not.found";
    public static final String ERROR_CAUSE_UNIQUENESS_CONFLICT = "error.cause.uniqueness.conflict";
    public static final String ERROR_CAUSE_VALIDATION = "error.cause.validation";
    public static final String ERROR_CAUSE_INTERNAL = "error.cause.internal";

    public static final String ERROR_INVALID_SORT_ORDER  = "{error.invalid.sort.order}";
    public static final String VALIDATION_INVALID_PHONE_FORMAT = "{error.invalid.phone.format}";
    public static final String VALIDATION_INVALID_LICENCE_PLATE_FORMAT = "{error.invalid.licence.plate.format}";
    public static final String VALIDATION_INVALID_LENGTH = "{error.invalid.length}";

    public static final String ERROR_DRIVER_WITH_ID_NOT_FOUND = "error.driver.with.id.not.found";
    public static final String ERROR_DRIVER_WITH_PHONE_ALREADY_EXISTS = "error.driver.with.phone.already.exists";
    public static final String ERROR_DRIVER_WITH_EMAIL_ALREADY_EXISTS="error.driver.with.email.already.exists";

    public static final String ERROR_CAR_WITH_ID_NOT_FOUND="error.car.with.id.not.found";
    public static final String ERROR_CAR_WITH_LICENCE_PLATE_ALREADY_EXISTS="error.car.with.license.plate.already.exists";

    public static final String ERROR_CAR_DETAILS_WITH_ID_NOT_FOUND="error.car.details.with.id.not.found";
    public static final String ERROR_CAR_DETAILS_WITH_CAR_ID_ALREADY_EXISTS="error.car.details.with.car.id.already.exists";

    public static final String ERROR_CAR_RELEASE_DATE_IS_AFTER_PRESENT="error.car.release.date.is.after.present";

    public static final String ERROR_CANT_READ_REQUEST="error.cant.read.request";
    public static final String ERROR_IF_PERSISTS_CONTACT_DEVELOPERS="error.if.persists.contact.developers";
}
