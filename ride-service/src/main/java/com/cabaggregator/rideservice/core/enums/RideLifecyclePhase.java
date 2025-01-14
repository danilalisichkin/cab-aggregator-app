package com.cabaggregator.rideservice.core.enums;

/**
 * Ride lifecycle phases that reflect the transition between different ride statuses.
 **/
public enum RideLifecyclePhase {
    REQUESTING,
    ACCEPTING,
    CANCELLING_BEFORE_START,
    WAITING_FOR_PASSENGER,
    STARTING,
    CANCELLING_WHEN_IN_PROGRESS,
    COMPLETING
}
