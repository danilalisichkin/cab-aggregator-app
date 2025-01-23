package com.cabaggregator.driverservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternalErrorTemplates {
    public static final String GOT_USER_WITHOUT_ROLE = "got user without role, his id =%s";
    public static final String GOT_USER_WITH_MORE_THAN_ONE_ROLE = "got user with > 1 roles, his id =%s";
}
