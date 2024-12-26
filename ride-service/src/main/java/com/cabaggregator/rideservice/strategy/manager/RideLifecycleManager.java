package com.cabaggregator.rideservice.strategy.manager;

import com.cabaggregator.rideservice.core.constant.ApplicationMessages;
import com.cabaggregator.rideservice.core.enums.RideLifecyclePhase;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.exception.ForbiddenException;
import com.cabaggregator.rideservice.strategy.RideLifecycleHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages ride status changing depending on user role.
 **/
@Component
public class RideLifecycleManager {

    private final Map<RideLifecyclePhase, RideLifecycleHandler> handlers;

    @Autowired
    public RideLifecycleManager(List<RideLifecycleHandler> handlerList) {
        this.handlers = handlerList.stream()
                .collect(Collectors.toMap(
                        RideLifecycleHandler::getSupportedPhase,
                        handler -> handler));
    }

    public void processLifecyclePhaseChange(Ride ride, RideLifecyclePhase newPhase) {
        RideLifecycleHandler handler = handlers.get(newPhase);
        if (handler == null) {
            throw new ForbiddenException(ApplicationMessages.STATUS_CHANGING_NOT_ALLOWED);
        }
        handler.handle(ride);
    }
}
