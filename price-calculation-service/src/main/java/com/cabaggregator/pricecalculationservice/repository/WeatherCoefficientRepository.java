package com.cabaggregator.pricecalculationservice.repository;

import com.cabaggregator.pricecalculationservice.entity.WeatherCoefficient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherCoefficientRepository extends JpaRepository<WeatherCoefficient, String> {
}
