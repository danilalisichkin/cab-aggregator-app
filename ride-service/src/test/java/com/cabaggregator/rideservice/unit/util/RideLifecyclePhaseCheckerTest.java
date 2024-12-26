package com.cabaggregator.rideservice.unit.util;

import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.util.RideLifecyclePhaseChecker;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class RideLifecyclePhaseCheckerTest {

    @Test
    void isRequesting_ShouldReturnTrue_WhenRideIsPreparedAndNewStatusIsRequested() {
        RideStatus oldStatus = RideStatus.PREPARED;
        RideStatus newStatus = RideStatus.REQUESTED;

        boolean actual = RideLifecyclePhaseChecker.isRequesting(oldStatus, newStatus);

        assertThat(actual).isTrue();
    }

    @Test
    void isAccepting_ShouldReturnTrue_WhenRideIsRequestedAndNewStatusIdArriving() {
        RideStatus oldStatus = RideStatus.REQUESTED;
        RideStatus newStatus = RideStatus.ARRIVING;

        boolean actual = RideLifecyclePhaseChecker.isAccepting(oldStatus, newStatus);

        assertThat(actual).isTrue();
    }

    @Test
    void isWaiting_ShouldReturnTrue_WhenDriverIsArrivedAndNewStatusIsWaiting() {
        RideStatus oldStatus = RideStatus.ARRIVING;
        RideStatus newStatus = RideStatus.WAITING;

        boolean actual = RideLifecyclePhaseChecker.isWaiting(oldStatus, newStatus);

        assertThat(actual).isTrue();
    }

    @Test
    void isStarting_ShouldReturnTrue_WhenOldStatusIsWaitingAndNewStatusIsInProgress() {
        RideStatus oldStatus = RideStatus.WAITING;
        RideStatus newStatus = RideStatus.IN_PROGRESS;

        boolean actual = RideLifecyclePhaseChecker.isStarting(oldStatus, newStatus);

        assertThat(actual).isTrue();
    }

    @Test
    void isCompleting_ShouldReturnTrue_WhenRideIsInProgressAndNewStatusIsCompleted() {
        RideStatus oldStatus = RideStatus.IN_PROGRESS;
        RideStatus newStatus = RideStatus.COMPLETED;

        boolean actual = RideLifecyclePhaseChecker.isCompleting(oldStatus, newStatus);

        assertThat(actual).isTrue();
    }

    @Test
    void isCancellingBeforeStart_ShouldReturnTrue_WhenRideWasRequestedAndNotStartedYetAndNewStatusIsCanceled() {
        RideStatus oldStatusAsRequested = RideStatus.REQUESTED;
        RideStatus oldStatusAsArriving = RideStatus.ARRIVING;
        RideStatus oldStatusAsWaiting = RideStatus.WAITING;
        RideStatus newStatus = RideStatus.CANCELED;

        boolean actual1 = RideLifecyclePhaseChecker.isCancellingBeforeStart(oldStatusAsRequested, newStatus);

        assertThat(actual1).isTrue();

        boolean actual2 = RideLifecyclePhaseChecker.isCancellingBeforeStart(oldStatusAsArriving, newStatus);

        assertThat(actual2).isTrue();

        boolean actual3 = RideLifecyclePhaseChecker.isCancellingBeforeStart(oldStatusAsWaiting, newStatus);

        assertThat(actual3).isTrue();
    }

    @Test
    void isCancellingWhenInProgress_ShouldReturnTrue_WhenRideIsInProgressAndNewStatusIsCanceled() {
        RideStatus oldStatus = RideStatus.IN_PROGRESS;
        RideStatus newStatus = RideStatus.CANCELED;

        boolean actual = RideLifecyclePhaseChecker.isCancellingWhenInProgress(oldStatus, newStatus);

        assertThat(actual).isTrue();
    }
}
