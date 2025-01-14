package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.service.RidePassengerService;
import com.cabaggregator.rideservice.service.RidePaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/passengers")
public class RidePassengerController {

    private final RidePassengerService ridePassengerService;

    private final RidePaymentService ridePaymentService;

    @GetMapping("/{passengerId}/rides")
    public ResponseEntity<PageDto<RideDto>> getPageOfPassengerRides(
            @PathVariable UUID passengerId,
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
            @Max(value = 40, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") RideSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) RideStatus status) {

        PageDto<RideDto> driverRides =
                ridePassengerService.getPageOfPassengerRides(passengerId, offset, limit, sortBy, sortOrder, status);

        return ResponseEntity.status(HttpStatus.OK).body(driverRides);
    }

    @GetMapping("/{passengerId}/rides/{id}")
    public ResponseEntity<RideDto> getRide(
            @PathVariable UUID passengerId,
            @PathVariable ObjectId id) {

        RideDto ride = ridePassengerService.getRide(passengerId, id);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PutMapping("/{passengerId}/rides/{id}")
    public ResponseEntity<RideDto> updateRide(
            @PathVariable UUID passengerId,
            @PathVariable ObjectId id,
            @RequestBody @Valid RideUpdatingDto updatingDto) {

        RideDto ride = ridePassengerService.updateRide(passengerId, id, updatingDto);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PatchMapping("/{passengerId}/rides/{id}/status")
    public ResponseEntity<RideDto> changeRideStatus(
            @PathVariable UUID passengerId,
            @PathVariable ObjectId id,
            @RequestBody RideStatus status) {

        RideDto ride = ridePassengerService.changeRideStatus(passengerId, id, status);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }
}
