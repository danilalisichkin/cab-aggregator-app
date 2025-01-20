package com.cabaggregator.passengerservice.util;

import com.cabaggregator.passengerservice.core.constant.InternalErrorTemplates;
import com.cabaggregator.passengerservice.exception.InternalErrorException;
import com.cabaggregator.passengerservice.security.enums.UserRole;
import com.cabaggregator.passengerservice.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
