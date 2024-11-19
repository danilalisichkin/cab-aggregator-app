package com.cabaggregator.driverservice.core.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationRegex {
    public static final String PHONE_BELARUS_FORMAT = "^375(15|29|33|44)\\d{7}$";
    public static final String LICENCE_PLATE_BELARUS_FORMAT = "^\\d{4} [ABEIKMHOPCTX]{2}-\\d$";
}
