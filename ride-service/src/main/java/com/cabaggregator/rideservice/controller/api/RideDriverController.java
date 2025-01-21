package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.service.RideDriverService;
import com.cabaggregator.rideservice.service.RidePaymentService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/drivers")
public class RideDriverController {

    private final RideDriverService rideDriverService;

    private final RidePaymentService ridePaymentService;

    @GetMapping("/{driverId}/rides")
    public ResponseEntity<PageDto<RideDto>> getPageOfDriverRides(
            @PathVariable UUID driverId,
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
            @Max(value = 40, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") RideSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) RideStatus status) {

        PageDto<RideDto> driverRides =
                rideDriverService.getPageOfDriverRides(driverId, offset, limit, sortBy, sortOrder, status);

        return ResponseEntity.status(HttpStatus.OK).body(driverRides);
    }

    @GetMapping("/{driverId}/rides/{id}")
    public ResponseEntity<RideDto> getRide(
            @PathVariable UUID driverId,
            @PathVariable ObjectId id) {
        RideDto ride = rideDriverService.getRide(driverId, id);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PatchMapping("/{driverId}/rides/{id}/payment-status")
    public ResponseEntity<RideDto> changeRidePaymentStatus(
            @PathVariable UUID driverId,
            @PathVariable ObjectId id,
            @RequestBody @NotNull RidePaymentStatus paymentStatus) {

        RideDto ride = ridePaymentService.changeRidePaymentStatus(driverId, id, paymentStatus);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PatchMapping("/{driverId}/rides/{id}/status")
    public ResponseEntity<RideDto> changeRideStatus(
            @PathVariable UUID driverId,
            @PathVariable ObjectId id,
            @RequestBody RideStatus status) {

        RideDto ride = rideDriverService.changeRideStatus(driverId, id, status);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }
}
