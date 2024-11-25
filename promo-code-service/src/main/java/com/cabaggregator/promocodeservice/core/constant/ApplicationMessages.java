package com.cabaggregator.promocodeservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String PROMO_CODE_ALREADY_EXISTS = "error.promo.code.already_exists";
    public static final String PROMO_CODE_END_DATE_IN_PAST = "error.promo.code.end.date.in.past";
    public static final String PROMO_CODE_NOT_FOUND = "error.promo.code.not.found";
    public static final String PROMO_CODE_EXPIRED = "error.promo.code.expired";
    public static final String PROMO_CODE_ALREADY_APPLIED = "error.promo.code.already.applied";
}
