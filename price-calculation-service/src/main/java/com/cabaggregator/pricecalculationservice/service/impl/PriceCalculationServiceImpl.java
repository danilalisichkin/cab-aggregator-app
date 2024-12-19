package com.cabaggregator.pricecalculationservice.service.impl;

import com.cabaggregator.pricecalculationservice.core.dto.PriceCalculationRequest;
import com.cabaggregator.pricecalculationservice.core.dto.PriceResponse;
import com.cabaggregator.pricecalculationservice.entity.DemandCoefficient;
import com.cabaggregator.pricecalculationservice.entity.RideFare;
import com.cabaggregator.pricecalculationservice.entity.WeatherCoefficient;
import com.cabaggregator.pricecalculationservice.service.DemandCoefficientService;
import com.cabaggregator.pricecalculationservice.service.GeoGridService;
import com.cabaggregator.pricecalculationservice.service.PriceCalculationService;
import com.cabaggregator.pricecalculationservice.service.RideFareService;
import com.cabaggregator.pricecalculationservice.service.WeatherCoefficientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PriceCalculationServiceImpl implements PriceCalculationService {

    private final GeoGridService geoGridService;

    private final RideFareService rideFareService;

    private final DemandCoefficientService demandCoefficientService;

    private final WeatherCoefficientService weatherCoefficientService;

    /**
     * Calculates price of ride using price by fare, demand coefficient and current weather state.
     **/
    @Override
    public PriceResponse calculatePrice(PriceCalculationRequest request) {
        String gridCell = getGridCell(request);

        PriceResponse response = new PriceResponse();

        calculateBasePrice(response, request);
        applyDemandAdjustment(response, request, gridCell);
        applyWeatherAdjustment(response, gridCell);

        return response;
    }

    /**
     * Calculates base price by ride fare.
     **/
    private void calculateBasePrice(PriceResponse response, PriceCalculationRequest request) {
        RideFare rideFare = rideFareService.getRideFare(request.fare());

        long priceByDuration = request.duration() * rideFare.getPricePerMinute() / 60;
        long priceByDistance = request.distance() * rideFare.getPricePerKilometer() / 1000;

        long maxPrice = Math.max(priceByDistance, priceByDuration);

        response.setPrice(maxPrice);
    }

    /**
     * Multiplies price by current demand coefficient.
     **/
    private void applyDemandAdjustment(PriceResponse response, PriceCalculationRequest request, String gridCell) {
        DemandCoefficient demandCoefficient =
                demandCoefficientService.getCurrentDemandCoefficient(gridCell, request.rideId());

        long priceIncreasedByDemand = (long) (response.getPrice() * demandCoefficient.getPriceCoefficient());

        response.setPrice(priceIncreasedByDemand);
        response.setDemand(demandCoefficient.getDemand());
    }

    /**
     * Multiplies price by price coefficient of current weather state.
     **/
    private void applyWeatherAdjustment(PriceResponse response, String gridCell) {
        WeatherCoefficient weatherCoefficient =
                weatherCoefficientService.getCurrentWeatherCoefficient(gridCell);

        long priceIncreasedByWeather = (long) (response.getPrice() * weatherCoefficient.getPriceCoefficient());

        response.setPrice(priceIncreasedByWeather);
        response.setWeather(weatherCoefficient.getWeather());
    }

    /**
     * Retrieves grid cell by request coordinates.
     * Given coordinates are reversed because of mismatch format in OpenRouteAPI and WeatherAPI (external).
     **/
    private String getGridCell(PriceCalculationRequest request) {
        return geoGridService.calculateGridCell(
                request.pickUpCoordinates().get(1),
                request.pickUpCoordinates().get(0));
    }
}
