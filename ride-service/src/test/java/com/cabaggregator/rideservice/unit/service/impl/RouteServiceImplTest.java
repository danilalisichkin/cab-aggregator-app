package com.cabaggregator.rideservice.unit.service.impl;

import com.cabaggregator.rideservice.client.OpenRouteApiClient;
import com.cabaggregator.rideservice.client.dto.RouteRequest;
import com.cabaggregator.rideservice.core.dto.route.RouteSummary;
import com.cabaggregator.rideservice.service.impl.RouteServiceImpl;
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
import java.util.List;

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
        List<List<Double>> routeCoordinates = RouteTestUtil.buildRouteCoordinates();
        RouteSummary routeSummary = RouteTestUtil.buildRouteSummary();

        try (MockedStatic<RouteResponseExtractor> mockedStatic = mockStatic(RouteResponseExtractor.class)) {
            when(openRouteApiClient.getDrivingCarRoute(any(RouteRequest.class)))
                    .thenReturn(Collections.emptyMap());
            mockedStatic.when(() -> RouteResponseExtractor.extractRouteSummary(anyMap()))
                    .thenReturn(routeSummary);

            RouteSummary actual = routeService.getRouteSummary(routeCoordinates);

            assertThat(actual).isEqualTo(routeSummary);

            verify(openRouteApiClient).getDrivingCarRoute(any(RouteRequest.class));
            mockedStatic.verify(() -> RouteResponseExtractor.extractRouteSummary(anyMap()));
        }
    }
}
