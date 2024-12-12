package com.cabaggregator.paymentservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationErrors {
    public static final String INVALID_PHONE_FORMAT = "{validation.invalid.phone.format}";
    public static final String INVALID_STRING_LENGTH = "{validation.invalid.string.length}";
    public static final String INVALID_STRING_MIN_LENGTH = "{validation.invalid.string.min.length}";
    public static final String INVALID_STRING_MAX_LENGTH = "{validation.invalid.string.max.length}";
    public static final String STRING_IS_EMPTY = "{validation.string.is.empty}";
    public static final String INVALID_NUMBER_VALUE = "{validation.invalid.number.value}";
    public static final String INVALID_NUMBER_MIN_VALUE = "{validation.invalid.number.min.value}";
    public static final String INVALID_NUMBER_MAX_VALUE = "{validation.invalid.number.max.value}";
    public static final String NUMBER_IS_NOT_POSITIVE = "{validation.number.is.not.positive}";
    public static final String NUMBER_IS_NOT_POSITIVE_OR_ZERO = "{validation.number.is.not.positive.or.zero}";
}
