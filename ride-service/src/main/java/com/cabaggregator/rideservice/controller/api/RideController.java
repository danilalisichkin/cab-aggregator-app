package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.RideAddingDto;
import com.cabaggregator.rideservice.core.RideDto;
import com.cabaggregator.rideservice.core.RideUpdatingDto;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.enums.PaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
@RequestMapping("api/v1/rides")
public class RideController {

    @GetMapping
    public ResponseEntity<PageDto<RideDto>> getPageOfRides(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "id") RideSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(name = "status") RideStatus status) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getRide(@PathVariable ObjectId id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<RideDto> createRide(@RequestBody RideAddingDto addingDto) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideDto> updateRide(
            @PathVariable ObjectId id,
            @RequestBody RideUpdatingDto updatingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RideDto> changeRideStatus(
            @PathVariable ObjectId id,
            @RequestBody RideStatus status) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/payment-status")
    public ResponseEntity<RideDto> changeRidePaymentStatus(
            @PathVariable ObjectId id,
            @RequestBody PaymentStatus paymentStatus) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
