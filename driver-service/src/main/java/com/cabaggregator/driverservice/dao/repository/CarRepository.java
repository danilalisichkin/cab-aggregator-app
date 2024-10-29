package com.cabaggregator.driverservice.dao.repository;

import com.cabaggregator.driverservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByLicensePlate(String licensePlate);

    boolean existsByLicensePlate(String licensePlate);
}
