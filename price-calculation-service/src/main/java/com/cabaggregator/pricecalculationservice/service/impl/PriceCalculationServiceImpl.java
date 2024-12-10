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

    @Override
    public PriceResponse calculatePrice(PriceCalculationRequest request) {
        String gridCell = getGridCell(request);

        PriceResponse response = new PriceResponse();

        calculatePriceByFair(response, request);
        calculatePriceByDemand(response, request, gridCell);
        calculatePriceByWeather(response, gridCell);

        return response;
    }

    private void calculatePriceByFair(PriceResponse response, PriceCalculationRequest request) {
        RideFare rideFare = rideFareService.getRideFare(request.fare());

        long priceByDuration = request.duration() * rideFare.getPricePerMinute() / 60;
        long priceByDistance = request.distance() * rideFare.getPricePerKilometer() / 1000;

        long maxPrice = Math.max(priceByDistance, priceByDuration);

        response.setPrice(maxPrice);
    }

    private void calculatePriceByDemand(PriceResponse response, PriceCalculationRequest request, String gridCell) {
        DemandCoefficient demandCoefficient =
                demandCoefficientService.getCurrentDemandCoefficient(request.rideId(), gridCell);

        long priceIncreasedByDemand = (long) (response.getPrice() * demandCoefficient.getPriceCoefficient());

        response.setPrice(priceIncreasedByDemand);
        response.setDemand(demandCoefficient.getDemand());
    }

    private void calculatePriceByWeather(PriceResponse response, String gridCell) {
        WeatherCoefficient weatherCoefficient =
                weatherCoefficientService.getCurrentWeatherCoefficient(gridCell);

        long priceIncreasedByWeather = (long) (response.getPrice() * weatherCoefficient.getPriceCoefficient());

        response.setPrice(priceIncreasedByWeather);
        response.setWeather(weatherCoefficient.getWeather());
    }

    private String getGridCell(PriceCalculationRequest request) {
        return geoGridService.calculateGridCell(
                request.pickUpCoordinates().get(1),
                request.pickUpCoordinates().get(0));
    }
}
