package com.cabaggregator.rideservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationRegex {
    public static final String SORT_ORDER = "(?i)^(asc|desc)$";
}
