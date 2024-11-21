package com.cabaggregator.rideservice.repository;

import com.cabaggregator.rideservice.entity.Ride;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends MongoRepository<Ride, ObjectId> {
    List<Ride> findByPassengerIdAndDriverId(String passengerId, String driverId);

    List<Ride> findByPassengerId(String passengerId);

    List<Ride> findByDriverId(String driverId);

    Page<Ride> findByStatus(RideStatus status, Pageable pageable);

    Page<Ride> findByStatusAndPassengerId(RideStatus status, String passengerId, Pageable pageable);

    Page<Ride> findByStatusAndDriverId(RideStatus status, String driverId, Pageable pageable);
}
