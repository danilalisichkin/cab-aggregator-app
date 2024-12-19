package com.cabaggregator.pricecalculationservice.service;

import com.cabaggregator.pricecalculationservice.entity.WeatherCoefficient;

public interface WeatherCoefficientService {
    WeatherCoefficient getCurrentWeatherCoefficient(String gridCell);
}
