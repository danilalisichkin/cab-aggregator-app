package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.enums.PaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.service.RidePaymentService;
import com.cabaggregator.rideservice.service.RideService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rides")
public class RideController {

    private final RideService rideService;

    private final RidePaymentService ridePaymentService;

    @GetMapping
    public ResponseEntity<PageDto<RideDto>> getPageOfRides(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
            @Max(value = 40, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") RideSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) RideStatus status) {

        PageDto<RideDto> rides = rideService.getPageOfRides(offset, limit, sortBy, sortOrder, status);

        return ResponseEntity.status(HttpStatus.OK).body(rides);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<PageDto<RideDto>> getPageOfDriverRides(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
            @Max(value = 40, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") RideSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) RideStatus status,
            @PathVariable UUID driverId) {

        PageDto<RideDto> driverRides =
                rideService.getPageOfDriverRides(offset, limit, sortBy, sortOrder, status, driverId);

        return ResponseEntity.status(HttpStatus.OK).body(driverRides);
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<PageDto<RideDto>> getPageOfPassengerRides(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
            @Max(value = 40, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") RideSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) RideStatus status,
            @PathVariable UUID passengerId) {

        PageDto<RideDto> driverRides =
                rideService.getPageOfPassengerRides(offset, limit, sortBy, sortOrder, status, passengerId);

        return ResponseEntity.status(HttpStatus.OK).body(driverRides);
    }

    @GetMapping("/available")
    public ResponseEntity<PageDto<RideDto>> getAvailableRides(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
            @Max(value = 40, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") RideSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        PageDto<RideDto> availableRides =
                rideService.getPageOfAvailableRides(offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(availableRides);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getRide(@PathVariable ObjectId id) {
        RideDto ride = rideService.getRide(id);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PostMapping
    public ResponseEntity<RideDto> createRide(@RequestBody @Valid RideAddingDto addingDto) {
        RideDto ride = rideService.createRide(addingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ride);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideDto> updateRide(
            @PathVariable ObjectId id,
            @RequestBody @Valid RideUpdatingDto updatingDto) {

        RideDto ride = rideService.updateRide(id, updatingDto);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RideDto> changeRideStatus(
            @PathVariable ObjectId id,
            @RequestBody @NotNull RideStatus status) {

        RideDto ride = rideService.changeRideStatus(id, status);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PatchMapping("/{id}/payment-status")
    public ResponseEntity<RideDto> changeRidePaymentStatus(
            @PathVariable ObjectId id,
            @RequestBody @NotNull PaymentStatus paymentStatus) {

        RideDto ride = ridePaymentService.changeRidePaymentStatus(id, paymentStatus);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }
}
