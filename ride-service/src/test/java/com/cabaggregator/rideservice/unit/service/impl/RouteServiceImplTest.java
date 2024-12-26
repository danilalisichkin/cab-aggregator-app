package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.client.OpenRouteApiClient;
import com.cabaggregator.rideservice.client.dto.RouteRequest;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.route.RouteSummary;
import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.service.impl.RouteServiceImpl;
import com.cabaggregator.rideservice.util.RideTestUtil;
import com.cabaggregator.rideservice.util.RouteResponseExtractor;
import com.cabaggregator.rideservice.util.RouteTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {
    @InjectMocks
    private RouteServiceImpl routeService;

    @Mock
    private OpenRouteApiClient openRouteApiClient;

    @Test
    void setRouteSummary_ShouldSetRouteSummary_WhenRouteServiceAvailable() {
        Ride actual = RideTestUtil.buildDefaultRide().toBuilder()
                .distance(null)
                .estimatedDuration(null)
                .build();
        RideAddingDto rideAddingDto = RideTestUtil.buildRideAddingDto();
        RouteSummary routeSummary = RouteTestUtil.buildRouteSummary();

        try (MockedStatic<RouteResponseExtractor> mockedStatic = mockStatic(RouteResponseExtractor.class)) {
            when(openRouteApiClient.getDrivingCarRoute(any(RouteRequest.class)))
                    .thenReturn(Collections.emptyMap());
            mockedStatic.when(() -> RouteResponseExtractor.extractRouteSummary(anyMap()))
                    .thenReturn(routeSummary);

            routeService.setRouteSummary(actual, rideAddingDto);

            assertThat(actual.getDistance()).isEqualTo(routeSummary.distance());
            assertThat(actual.getEstimatedDuration()).isEqualTo(routeSummary.duration());

            verify(openRouteApiClient).getDrivingCarRoute(any(RouteRequest.class));
            mockedStatic.verify(() -> RouteResponseExtractor.extractRouteSummary(anyMap()));
        }
    }
}
