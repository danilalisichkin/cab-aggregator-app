package com.cabaggregator.rideservice.validator;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.enums.UserRole;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidator {
    public void validateUserIsPassenger(UserRole role) {
        if (!role.equals(UserRole.PASSENGER)) {
            throw new ForbiddenException(
                    ApplicationMessages.USER_MUST_HAVE_ROLE,
                    UserRole.PASSENGER.getValue());
        }
    }

    public void validateUserIsDriver(UserRole role) {
        if (!role.equals(UserRole.DRIVER)) {
            throw new ForbiddenException(
                    ApplicationMessages.USER_MUST_HAVE_ROLE,
                    UserRole.DRIVER.getValue());
        }
    }

    public void validateUserIsPassengerOrDriver(UserRole role) {
        if (!role.equals(UserRole.PASSENGER) && !role.equals(UserRole.DRIVER)) {
            throw new ForbiddenException(
                    ApplicationMessages.USER_MUST_HAVE_ROLE,
                    UserRole.PASSENGER.getValue() + " / " + UserRole.DRIVER.getValue());
        }
    }
}
