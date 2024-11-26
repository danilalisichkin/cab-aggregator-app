package com.example.pricecalculationservice.integration.repository;

import com.example.pricecalculationservice.config.PostgreSQLContainerConfig;
import com.example.pricecalculationservice.entity.WeatherCoefficient;
import com.example.pricecalculationservice.repository.WeatherCoefficientRepository;
import com.example.pricecalculationservice.util.WeatherCoefficientTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Testcontainers
@ContextConfiguration(classes = PostgreSQLContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WeatherCoefficientRepositoryTest {
    @Autowired
    private WeatherCoefficientRepository weatherCoefficientRepository;

    @Test
    void findByWeather_ShouldReturnWeatherCoefficient_WhenWeatherCoefficientExists() {
        WeatherCoefficient weatherCoefficient = WeatherCoefficientTestUtil.getWeatherCoefficientBuilder()
                .id(null)
                .build();
        weatherCoefficientRepository.save(weatherCoefficient);

        Optional<WeatherCoefficient> actual =
                weatherCoefficientRepository.findByWeather(weatherCoefficient.getWeather());

        assertThat(actual)
                .isNotEmpty()
                .contains(weatherCoefficient);
    }

    @Test
    void findByWeather_ShouldReturnEmptyOptional_WhenWeatherCoefficientDoesNotExist() {
        Optional<WeatherCoefficient> actual =
                weatherCoefficientRepository.findByWeather(WeatherCoefficientTestUtil.WEATHER);

        assertThat(actual).isEmpty();
    }
}
