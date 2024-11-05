package com.cabaggregator.rideservice.repository;

import com.cabaggregator.rideservice.entity.RideRate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RideRateRepository extends MongoRepository<RideRate, ObjectId> {
    Optional<RideRate> findByRideId(ObjectId rideId);
}
