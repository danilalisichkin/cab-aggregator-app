package com.example.pricecalculationservice.integration.repository;

import com.example.pricecalculationservice.entity.WeatherCoefficient;
import com.example.pricecalculationservice.repository.WeatherCoefficientRepository;
import com.example.pricecalculationservice.util.WeatherCoefficientTestUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WeatherCoefficientRepositoryTest {
    @Autowired
    private WeatherCoefficientRepository weatherCoefficientRepository;

    @Container
    public static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("price_calculation_database")
            .withUsername("postgres")
            .withPassword("root");

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Test
    void findByWeather_ShouldReturnWeatherCoefficient_WhenWeatherCoefficientExists() {
        WeatherCoefficient weatherCoefficient = WeatherCoefficientTestUtil.buildWeatherCoefficient();
        weatherCoefficient.setId(null);

        weatherCoefficientRepository.save(weatherCoefficient);

        Optional<WeatherCoefficient> result = weatherCoefficientRepository.findByWeather(weatherCoefficient.getWeather());

        assertThat(result)
                .isNotEmpty()
                .contains(weatherCoefficient);
    }

    @Test
    void findByWeather_ShouldReturnEmptyOptional_WhenWeatherCoefficientDoesNotExist() {
        Optional<WeatherCoefficient> result = weatherCoefficientRepository.findByWeather(WeatherCoefficientTestUtil.WEATHER);

        assertThat(result).isEmpty();
    }
}
