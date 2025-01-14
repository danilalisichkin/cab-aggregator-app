package com.cabaggregator.rideservice.util;

import com.cabaggregator.rideservice.core.dto.route.RouteSummary;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RouteTestUtil {
    public static final Long DISTANCE = 2400L;
    public static final Long ESTIMATED_DURATION = 720L;

    public static RouteSummary buildRouteSummary() {
        return new RouteSummary(
                ESTIMATED_DURATION,
                DISTANCE);
    }

    public static List<List<Double>> buildRouteCoordinates() {
        return List.of(RideTestUtil.PICK_UP_COORDINATES, RideTestUtil.DROP_OFF_COORDINATES);
    }
}
