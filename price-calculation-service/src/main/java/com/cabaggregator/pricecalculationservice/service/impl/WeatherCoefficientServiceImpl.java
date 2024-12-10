package com.cabaggregator.pricecalculationservice.service.impl;

import com.cabaggregator.pricecalculationservice.client.WeatherApiClient;
import com.cabaggregator.pricecalculationservice.entity.WeatherCoefficient;
import com.cabaggregator.pricecalculationservice.exception.InternalErrorException;
import com.cabaggregator.pricecalculationservice.repository.WeatherCoefficientRepository;
import com.cabaggregator.pricecalculationservice.service.WeatherCoefficientService;
import com.cabaggregator.pricecalculationservice.util.WeatherResponseExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherCoefficientServiceImpl implements WeatherCoefficientService {

    private final WeatherCoefficientRepository weatherCoefficientRepository;

    private final WeatherApiClient weatherApiClient;

    @Override
    @Cacheable(value = "weatherCache", key = "#gridCell", unless = "#result == null")
    public WeatherCoefficient getCurrentWeatherCoefficient(String gridCell) {

        Map<String, Object> weatherResponse = weatherApiClient.getCurrentWeather(gridCell);
        String currentWeather = WeatherResponseExtractor.getCurrentWeatherState(weatherResponse);

        return getWeatherCoefficient(currentWeather);
    }

    private WeatherCoefficient getWeatherCoefficient(String weather) {
        return weatherCoefficientRepository
                .findById(weather)
                .orElseThrow(() -> new InternalErrorException(
                        String.format("no weather coefficient found for weather = %s", weather)));
    }
}
