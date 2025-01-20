package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import com.cabaggregator.rideservice.service.RideService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rides")
public class RideController {

    private final RideService rideService;

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

    @GetMapping("/requests")
    public ResponseEntity<PageDto<RideDto>> getRequestedRides(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
            @Max(value = 40, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") RideSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        PageDto<RideDto> availableRides =
                rideService.getPageOfRequestedRides(offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(availableRides);
    }

    @PutMapping("/rides/requests/{id}")
    public ResponseEntity<RideDto> acceptRide(
            @PathVariable ObjectId id) {

        RideDto acceptedRide = rideService.acceptRide(id);

        return ResponseEntity.status(HttpStatus.OK).body(acceptedRide);
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
}
