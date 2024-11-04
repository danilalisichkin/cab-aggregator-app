package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.dto.PagedDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rides")
public class RideController {
    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getRide(@PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/rate")
    public ResponseEntity<RideDto> getRideRate(@PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/rates")
    public ResponseEntity<PagedDto<RideRateDto>> getRideRates(
            @RequestParam(required = false, name = "passenger_id") Long passengerId,
            @RequestParam(required = false, name = "driver_id") Long driverId) {

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PagedDto<RideDto>> getPageOfRides(
            @RequestParam(required = false, name = "page", defaultValue = "1") int pageNumber,
            @RequestParam(required = false, name = "size", defaultValue = "10") int pageSize,
            @RequestParam(required = false, name = "status", defaultValue = "any") String status,
            @RequestParam(required = false, name = "passenger-id") Long passengerId,
            @RequestParam(required = false, name = "driver-id") Long driverId,
            @RequestParam(required = false, name = "order", defaultValue = "desc") String sortOrder) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/book")
    public ResponseEntity<RideDto> createRideBooking(
            @RequestBody RideBookingAddingDto addingDto) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<RideRateDto> setRideRate(
            @PathVariable ObjectId id,
            @RequestBody RideRateDto rate) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/book")
    public ResponseEntity<RideDto> updateRideBooking(
            @PathVariable ObjectId id,
            @RequestBody RideBookingUpdatingDto updatingDto) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideDto> updateRide(
            @PathVariable ObjectId id,
            @RequestBody RideUpdatingDto updatingDto) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RideDto> changeRideStatus(
            @PathVariable ObjectId id,
            @RequestBody String status) {

        return ResponseEntity.ok().build();
    }
}
