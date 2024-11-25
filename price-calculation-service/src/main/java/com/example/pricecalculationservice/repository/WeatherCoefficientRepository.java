package com.example.pricecalculationservice.repository;

import com.example.pricecalculationservice.entity.WeatherCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherCoefficientRepository extends JpaRepository<WeatherCoefficient, Integer> {
    Optional<WeatherCoefficient> findByWeather(String weather);
}
