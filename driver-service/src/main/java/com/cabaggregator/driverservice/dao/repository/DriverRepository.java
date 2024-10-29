package com.cabaggregator.driverservice.dao.repository;

import com.cabaggregator.driverservice.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
