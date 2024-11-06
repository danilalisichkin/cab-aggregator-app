package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.dto.page.PagedDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.promo.RidePromoCodeDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateSettingDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ResponseEntity<RideRateDto> getRideRate(@PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/promo_code")
    public ResponseEntity<RidePromoCodeDto> getRidePromoCode(@PathVariable ObjectId id) {

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
            @RequestBody RideRateSettingDto rate) {

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

    @PatchMapping("/{id}/status")
    public ResponseEntity<RideDto> changeRideStatus(
            @PathVariable ObjectId id,
            @RequestBody String status) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/promo_code")
    public ResponseEntity<RidePromoCodeDto> setRidePromoCode(
            @PathVariable ObjectId id,
            @RequestBody String code) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/promo_code")
    public ResponseEntity<Void> cancelRidePromoCode(@PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }
}
