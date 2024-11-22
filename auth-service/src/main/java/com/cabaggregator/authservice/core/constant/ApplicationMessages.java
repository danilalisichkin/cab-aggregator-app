package com.cabaggregator.authservice.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationMessages {
    public static final String INVALID_REFRESH_TOKEN = "error.invalid.refresh.token";
    public static final String INVALID_ACCESS_TOKEN = "error.invalid.access.token";
    public static final String REFRESH_TOKEN_EXPIRED = "error.refresh.token.expired";
    public static final String ACCESS_TOKEN_EXPIRED = "error.access.token.expired";
    public static final String REGISTER_USER_WITH_ROLE_PROHIBITED = "error.register.user.with.role.prohibited";
    public static final String REGISTER_USER_WITH_USED_EMAIL = "error.register.user.with.used.email";
    public static final String REGISTER_USER_WITH_USED_PHONE = "error.register.user.with.used.phone";
    public static final String USER_WITH_ID_NOT_FOUND = "error.user.with.id.not.found";
    public static final String USER_NOT_FOUND = "error.user.not.found";
    public static final String USER_ROLE_NOT_FOUND = "error.user.role.not.found";
    public static final String WRONG_LOGIN_OR_PASSWORD = "error.wrong.login.or.password";
}
