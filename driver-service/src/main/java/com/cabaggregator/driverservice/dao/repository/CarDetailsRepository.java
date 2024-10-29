package com.cabaggregator.driverservice.dao.repository;

import com.cabaggregator.driverservice.entities.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
}
