package com.cabaggregator.pricecalculationservice.unit.service.impl;

import com.cabaggregator.pricecalculationservice.client.WeatherApiClient;
import com.cabaggregator.pricecalculationservice.entity.WeatherCoefficient;
import com.cabaggregator.pricecalculationservice.exception.InternalErrorException;
import com.cabaggregator.pricecalculationservice.repository.WeatherCoefficientRepository;
import com.cabaggregator.pricecalculationservice.service.impl.WeatherCoefficientServiceImpl;
import com.cabaggregator.pricecalculationservice.util.GeoGridTestUtil;
import com.cabaggregator.pricecalculationservice.util.WeatherCoefficientTestUtil;
import com.cabaggregator.pricecalculationservice.util.WeatherResponseExtractor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class WeatherCoefficientServiceImplTest {
    @InjectMocks
    private WeatherCoefficientServiceImpl weatherCoefficientService;

    @Mock
    private WeatherCoefficientRepository weatherCoefficientRepository;

    @Mock
    private WeatherApiClient weatherApiClient;

    @Test
    void getCurrentWeatherCoefficient_ShouldReturnWeatherCoefficient_WhenWeatherCoefficientFound() {
        String gridCell = GeoGridTestUtil.GRID_CELL;
        WeatherCoefficient weatherCoefficient = WeatherCoefficientTestUtil.buildDefaultWeatherCoefficient();
        Map<String, Object> weatherResponse = WeatherCoefficientTestUtil.WEATHER_RESPONSE;

        when(weatherApiClient.getCurrentWeather(gridCell)).thenReturn(weatherResponse);

        try (MockedStatic<WeatherResponseExtractor> mockedStatic = mockStatic(WeatherResponseExtractor.class)) {
            mockedStatic.when(() -> WeatherResponseExtractor.getCurrentWeatherState(weatherResponse))
                    .thenReturn(weatherCoefficient.getWeather());

            when(weatherCoefficientRepository.findById(weatherCoefficient.getWeather()))
                    .thenReturn(Optional.of(weatherCoefficient));

            WeatherCoefficient actual = weatherCoefficientService.getCurrentWeatherCoefficient(gridCell);

            assertThat(actual)
                    .isNotNull()
                    .isEqualTo(weatherCoefficient);

            verify(weatherApiClient).getCurrentWeather(gridCell);
            mockedStatic.verify(() -> WeatherResponseExtractor.getCurrentWeatherState(weatherResponse));
            verify(weatherCoefficientRepository).findById(weatherCoefficient.getWeather());
        }
    }

    @Test
    void getCurrentWeatherCoefficient_ShouldThrowInternalErrorException_WhenWeatherCoefficientNotFound() {
        String gridCell = GeoGridTestUtil.GRID_CELL;
        String weather = WeatherCoefficientTestUtil.WEATHER;
        Map<String, Object> weatherResponse = WeatherCoefficientTestUtil.WEATHER_RESPONSE;

        when(weatherApiClient.getCurrentWeather(gridCell)).thenReturn(weatherResponse);

        try (MockedStatic<WeatherResponseExtractor> mockedStatic = mockStatic(WeatherResponseExtractor.class)) {
            mockedStatic.when(() -> WeatherResponseExtractor.getCurrentWeatherState(weatherResponse))
                    .thenReturn(weather);

            when(weatherCoefficientRepository.findById(weather))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(
                    () -> weatherCoefficientService.getCurrentWeatherCoefficient(gridCell))
                    .isInstanceOf(InternalErrorException.class);

            verify(weatherApiClient).getCurrentWeather(gridCell);
            mockedStatic.verify(() -> WeatherResponseExtractor.getCurrentWeatherState(weatherResponse));
            verify(weatherCoefficientRepository).findById(weather);
        }
    }
}
