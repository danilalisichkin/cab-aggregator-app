package com.cabaggregator.driverservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationRegex {
    public static final String SORT_ORDER = "(?i)^(asc|desc)$";
    public static final String PHONE_BELARUS_FORMAT = "^375(15|29|33|44)\\d{7}$";
    public static final String LICENCE_PLATE_BELARUS_FORMAT = "^\\d{4} [АВЕІКМНОРСТХ]{2}-\\d$";
}
