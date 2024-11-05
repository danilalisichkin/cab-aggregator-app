package com.cabaggregator.driverservice.repository;

import com.cabaggregator.driverservice.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
    Optional<CarDetails> findByCarId(long carId);

    boolean existsByCarId(long carId);
}
