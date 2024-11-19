package com.cabaggregator.authservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class KeyCloakErrorMessages {
    public static final String USER_WITH_SAME_EMAIL_EXISTS = "User exists with same email";
    public static final String USER_WITH_SAME_USERNAME_EXISTS = "User exists with same username";
    public static final String INVALID_REFRESH_TOKEN = "Invalid refresh token";
    public static final String REFRESH_TOKEN_EXPIRED = "Token is not active";
}
