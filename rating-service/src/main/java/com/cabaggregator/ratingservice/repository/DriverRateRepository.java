package com.cabaggregator.ratingservice.repository;

import com.cabaggregator.ratingservice.entity.DriverRate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverRateRepository extends MongoRepository<DriverRate, ObjectId> {
    Optional<DriverRate> findByDriverIdAndRideId(UUID driverId, ObjectId rideId);

    boolean existsByDriverIdAndRideId(UUID driverId, ObjectId rideId);
}
