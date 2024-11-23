package com.cabaggregator.ratingservice.repository;

import com.cabaggregator.ratingservice.entity.PassengerRate;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PassengerRateRepository extends MongoRepository<PassengerRate, ObjectId> {
    Optional<PassengerRate> findByPassengerIdAndRideId(UUID passengerId, ObjectId rideId);

    boolean existsByPassengerIdAndRideId(UUID passengerId, ObjectId rideId);
}
