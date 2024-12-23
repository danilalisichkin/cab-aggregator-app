package com.cabaggregator.pricecalculationservice.unit.service.impl;

import com.cabaggregator.pricecalculationservice.core.dto.PriceCalculationRequest;
import com.cabaggregator.pricecalculationservice.core.dto.PriceResponse;
import com.cabaggregator.pricecalculationservice.entity.DemandCoefficient;
import com.cabaggregator.pricecalculationservice.entity.RideFare;
import com.cabaggregator.pricecalculationservice.entity.WeatherCoefficient;
import com.cabaggregator.pricecalculationservice.service.DemandCoefficientService;
import com.cabaggregator.pricecalculationservice.service.GeoGridService;
import com.cabaggregator.pricecalculationservice.service.RideFareService;
import com.cabaggregator.pricecalculationservice.service.WeatherCoefficientService;
import com.cabaggregator.pricecalculationservice.service.impl.PriceCalculationServiceImpl;
import com.cabaggregator.pricecalculationservice.util.DemandCoefficientTestUtil;
import com.cabaggregator.pricecalculationservice.util.GeoGridTestUtil;
import com.cabaggregator.pricecalculationservice.util.PriceCalculationTestUtil;
import com.cabaggregator.pricecalculationservice.util.RideFareTestUtil;
import com.cabaggregator.pricecalculationservice.util.WeatherCoefficientTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class PriceCalculationServiceImplTest {
    @InjectMocks
    private PriceCalculationServiceImpl priceCalculationService;

    @Mock
    private GeoGridService geoGridService;

    @Mock
    private RideFareService rideFareService;

    @Mock
    private DemandCoefficientService demandCoefficientService;

    @Mock
    private WeatherCoefficientService weatherCoefficientService;

    @Test
    void calculatePrice_ShouldReturnPriceResponse_WhenAllPricePolicyFound() {
        PriceCalculationRequest request = PriceCalculationTestUtil.getPriceCalculationRequest();
        List<Double> coordinates = request.pickUpCoordinates();
        String gridCell = GeoGridTestUtil.GRID_CELL;
        RideFare rideFare = RideFareTestUtil.getRideFareBuilder().build();
        DemandCoefficient demandCoefficient =
                DemandCoefficientTestUtil.buildStandardDemandCoefficient();
        WeatherCoefficient weatherCoefficient =
                WeatherCoefficientTestUtil.getWeatherCoefficientBuilder().build();
        PriceResponse priceResponse = PriceCalculationTestUtil.getPriceResponse();

        when(geoGridService.calculateGridCell(coordinates.get(1), coordinates.get(0)))
                .thenReturn(gridCell);
        when(rideFareService.getRideFare(request.fare()))
                .thenReturn(rideFare);
        when(demandCoefficientService.getCurrentDemandCoefficient(gridCell, request.rideId()))
                .thenReturn(demandCoefficient);
        when(weatherCoefficientService.getCurrentWeatherCoefficient(gridCell))
                .thenReturn(weatherCoefficient);

        PriceResponse actualPriceResponse = priceCalculationService.calculatePrice(request);

        assertThat(actualPriceResponse)
                .isNotNull()
                .isEqualTo(priceResponse);

        verify(geoGridService).calculateGridCell(coordinates.get(1), coordinates.get(0));
        verify(rideFareService).getRideFare(request.fare());
        verify(demandCoefficientService).getCurrentDemandCoefficient(gridCell, request.rideId());
        verify(weatherCoefficientService).getCurrentWeatherCoefficient(gridCell);
    }
}
