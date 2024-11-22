package com.cabaggregator.driverservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCauses {
    public static final String BAD_REQUEST = "error.cause.bad.request";
    public static final String NOT_FOUND = "error.cause.not.found";
    public static final String UNIQUENESS_CONFLICT = "error.cause.uniqueness.conflict";
    public static final String VALIDATION = "error.cause.validation";
    public static final String INTERNAL = "error.cause.internal";
    public static final String CANT_READ_REQUEST = "error.cant.read.request";
    public static final String CONTACT_DEVELOPERS = "error.if.persists.contact.developers";
}
