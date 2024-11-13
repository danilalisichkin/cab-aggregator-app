package com.cabaggregator.driverservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationErrors {
    public static final String INVALID_SORT_ORDER  = "{validation.invalid.sort.order}";
    public static final String INVALID_PHONE_FORMAT = "{validation.invalid.phone.format}";
    public static final String INVALID_LICENCE_PLATE_FORMAT = "{validation.invalid.licence.plate.format}";
    public static final String INVALID_LENGTH = "{validation.invalid.length}";
}
