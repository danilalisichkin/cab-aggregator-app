package com.cabaggregator.ratingservice.repository;

import com.cabaggregator.ratingservice.entity.DriverRate;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverRateRepository extends MongoRepository<DriverRate, ObjectId> {
    Page<DriverRate> findAllByDriverId(UUID driverId, Pageable pageable);

    Optional<DriverRate> findByDriverIdAndRideId(UUID driverId, ObjectId rideId);

    boolean existsByDriverIdAndRideId(UUID driverId, ObjectId rideId);
}
