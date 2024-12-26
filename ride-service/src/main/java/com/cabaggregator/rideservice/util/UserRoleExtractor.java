package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.core.constant.InternalErrorTemplates;
import com.cabaggregator.rideservice.exception.InternalErrorException;
import com.cabaggregator.rideservice.security.enums.UserRole;
import com.cabaggregator.rideservice.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Extracts business user role from security context.
 * Used to control access to a resource that can be accessed by users with different roles.
 **/
@Component
@RequiredArgsConstructor
public class UserRoleExtractor {

    private final SecurityUtil securityUtil;

    public UserRole extractCurrentUserRole() {
        List<UserRole> roles =
                securityUtil.getUserAuthoritiesFromSecurityContext().stream()
                        .map(this::toUserRole)
                        .filter(Objects::nonNull)
                        .toList();

        if (roles.size() != 1) {
            UUID userId = securityUtil.getUserIdFromSecurityContext();
            String errorTemplate = roles.isEmpty()
                    ? InternalErrorTemplates.GOT_USER_WITHOUT_ROLE
                    : InternalErrorTemplates.GOT_USER_WITH_MORE_THAN_ONE_ROLE;

            throw new InternalErrorException(String.format(errorTemplate, userId.toString()));
        }

        return roles.getFirst();
    }

    private UserRole toUserRole(String role) {
        try {
            return UserRole.valueOf(role);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
