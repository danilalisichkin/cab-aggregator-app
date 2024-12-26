package com.cabaggregator.rideservice.repository;

import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.entity.Ride;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface RideRepository extends MongoRepository<Ride, ObjectId> {
    Page<Ride> findAllByPassengerId(UUID passengerId, Pageable pageable);

    Page<Ride> findAllByDriverId(UUID driverId, Pageable pageable);

    Page<Ride> findAllByPassengerIdAndStatus(UUID passengerId, RideStatus status, Pageable pageable);

    Page<Ride> findAllByDriverIdAndStatus(UUID driverId, RideStatus status, Pageable pageable);

    Page<Ride> findAllByStatus(RideStatus status, Pageable pageable);

    boolean existsByDriverIdAndStatusNotIn(UUID driverId, Set<RideStatus> statuses);

    boolean existsByPassengerIdAndStatusNotIn(UUID passengerId, Set<RideStatus> statuses);
}
