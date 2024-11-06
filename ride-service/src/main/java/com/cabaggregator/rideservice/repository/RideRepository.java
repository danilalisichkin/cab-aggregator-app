package com.cabaggregator.rideservice.repository;

import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RideRepository extends MongoRepository<Ride, ObjectId> {
    Optional<Ride> findByPassengerId(Long passengerId);

    Optional<Ride> findByDriverId(Long driverId);

    Optional<Ride> findByStatus(RideStatus status);
}
