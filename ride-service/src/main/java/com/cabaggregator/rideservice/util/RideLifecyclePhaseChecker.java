package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.core.enums.RideStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Provides static boolean expressions to check ride lifecycle phases.
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideLifecyclePhaseChecker {
    public static boolean isPrepared(RideStatus status) {
        return RideStatus.PREPARED.equals(status);
    }

    public static boolean isRequested(RideStatus status) {
        return RideStatus.REQUESTED.equals(status);
    }

    public static boolean isRequesting(RideStatus oldStatus, RideStatus newStatus) {
        return isRequested(newStatus) && isPrepared(oldStatus);
    }

    public static boolean isArriving(RideStatus status) {
        return status.equals(RideStatus.ARRIVING);
    }

    public static boolean isAccepting(RideStatus oldStatus, RideStatus newStatus) {
        return isArriving(newStatus) && isRequested(oldStatus);
    }

    public static boolean isWaiting(RideStatus oldStatus, RideStatus newStatus) {
        return isWaiting(newStatus) && isArriving(oldStatus);
    }

    public static boolean isInProgress(RideStatus status) {
        return RideStatus.IN_PROGRESS.equals(status);
    }

    public static boolean isStarting(RideStatus oldStatus, RideStatus newStatus) {
        return isInProgress(newStatus) && isWaiting(oldStatus);
    }

    public static boolean isCompleting(RideStatus status) {
        return RideStatus.COMPLETED.equals(status);
    }

    public static boolean isWaiting(RideStatus status) {
        return RideStatus.WAITING.equals(status);
    }

    public static boolean isCompleting(RideStatus oldStatus, RideStatus newStatus) {
        return isCompleting(newStatus) && isInProgress(oldStatus);
    }

    public static boolean isCancelling(RideStatus status) {
        return RideStatus.CANCELED.equals(status);
    }

    public static boolean isCancellingBeforeStart(RideStatus oldStatus, RideStatus newStatus) {
        return isCancelling(newStatus) && (isRequested(oldStatus) || isArriving(oldStatus) || isWaiting(oldStatus));
    }

    public static boolean isCancellingWhenInProgress(RideStatus oldStatus, RideStatus newStatus) {
        return isCancelling(newStatus) && isInProgress(oldStatus);
    }
}
