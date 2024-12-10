package com.cabaggregator.ratingservice.validator;

import com.cabaggregator.ratingservice.core.constant.ApplicationMessages;
import com.cabaggregator.ratingservice.exception.ForbiddenException;
import com.cabaggregator.ratingservice.security.enums.UserRole;
import com.cabaggregator.ratingservice.security.util.SecurityUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserRoleValidator {
    public void validateUserIsDriverOrAdmin(UUID driverId) {
        validateUserRole(UserRole.DRIVER, driverId);
    }

    public void validateUserIsPassengerOrAdmin(UUID passengerId) {
        validateUserRole(UserRole.PASSENGER, passengerId);
    }

    private void validateUserRole(UserRole role, UUID userIdToCheck) {
        List<String> authorities = SecurityUtil.getUserAuthoritiesFromSecurityContext();
        UUID currentUserId = SecurityUtil.getUserIdFromSecurityContext();

        if (authorities.contains(role.name()) && !currentUserId.equals(userIdToCheck)) {
            throw new ForbiddenException(ApplicationMessages.CANT_GET_RATES_OF_OTHER_USER);
        }
    }
}
