package com.cabaggregator.rideservice.strategy.manager;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.security.enums.UserRole;
import com.cabaggregator.rideservice.strategy.RideStatusChangingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages ride status changing depending on user role.
 **/
@Component
public class RideStatusChangingManager {

    private final Map<UserRole, RideStatusChangingHandler> handlers;

    @Autowired
    public RideStatusChangingManager(List<RideStatusChangingHandler> strategyList) {
        this.handlers = strategyList.stream()
                .collect(Collectors.toMap(
                        RideStatusChangingHandler::getSupportedRole,
                        handler -> handler));
    }

    public void processRideStatusChanging(Ride ride, UserRole userRole, RideStatus newRideStatus) {
        RideStatusChangingHandler handler = handlers.get(userRole);
        if (handler == null) {
            throw new ForbiddenException(ApplicationMessages.STATUS_CHANGING_NOT_ALLOWED);
        }
        handler.handle(ride, newRideStatus);
    }
}
