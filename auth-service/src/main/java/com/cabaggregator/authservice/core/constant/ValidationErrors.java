package com.cabaggregator.authservice.core.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationErrors {
    public static final String INVALID_PHONE_FORMAT = "{validation.invalid.phone.format}";
    public static final String INVALID_LOGIN_IDENTIFIER = "{validation.invalid.login.identifier}";
    public static final String INVALID_STRING_LENGTH = "{validation.invalid.string.length}";
    public static final String INVALID_STRING_MIN_LENGTH = "{validation.invalid.string.min.length}";
    public static final String INVALID_STRING_MAX_LENGTH = "{validation.invalid.string.max.length}";
    public static final String INVALID_NUMBER_VALUE = "{validation.invalid.number.value}";
    public static final String INVALID_NUMBER_MIN_VALUE = "{validation.invalid.number.min.value}";
    public static final String INVALID_NUMBER_MAX_VALUE = "{validation.invalid.number.max.value}";
    public static final String INVALID_UUID_FORMAT = "{validation.invalid.uuid.format}";
}
