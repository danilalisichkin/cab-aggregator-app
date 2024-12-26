package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.core.dto.route.RouteSummary;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RouteTestUtil {
    public static final Long DISTANCE = 2400L;
    public static final Long ESTIMATED_DURATION = 720L;

    public static RouteSummary buildRouteSummary() {
        return new RouteSummary(
                ESTIMATED_DURATION,
                DISTANCE);
    }
}
