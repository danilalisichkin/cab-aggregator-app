package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.page.PagedDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.enums.sort.RideSort;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import com.cabaggregator.rideservice.service.RideService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public ResponseEntity<PagedDto<RideDto>> getPageOfRides(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestParam(name = "offset") @Positive Integer offset,
            @RequestParam(name = "limit") @Positive Integer limit,
            @RequestParam(name = "sort") RideSort sort,
            @RequestParam(name = "status") RideStatus status) {

        PagedDto<RideDto> page = rideService.getPageOfRides(token, offset, limit, sort, status);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getRide(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id) {

        RideDto ride = rideService.getRideById(accessToken, id);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @GetMapping("/{id}/rate")
    public ResponseEntity<RideRateDto> getRideRate(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id) {

        RideRateDto rate = rideService.getRideRate(accessToken, id);

        return ResponseEntity.status(HttpStatus.OK).body(rate);
    }

    @PostMapping
    public ResponseEntity<RideDto> createRideOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestBody @Valid RideOrderAddingDto addingDto) {

        RideDto ride = rideService.orderRide(accessToken, addingDto);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<RideRateDto> setRideRate(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id,
            @RequestBody @NotNull
            @Min(value = 1, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
            @Max(value = 5, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
            Integer rate) {

        RideRateDto rideRate = rideService.setRideRate(accessToken, id, rate);

        return ResponseEntity.status(HttpStatus.OK).body(rideRate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideDto> updateRideOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id,
            @RequestBody @Valid RideOrderUpdatingDto updatingDto) {

        RideDto ride = rideService.updateRideOrder(accessToken, id, updatingDto);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RideDto> changeRideStatus(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id) {

        RideDto ride = rideService.changeRideStatus(accessToken, id);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }

    @PostMapping("/{id}/promo-code")
    public ResponseEntity<RideDto> setRidePromoCode(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id,
            @RequestBody @NotEmpty
            @Size(min = 2, max = 50, message = ValidationErrors.INVALID_STRING_LENGTH) String code) {

        RideDto ride = rideService.applyPromoCode(accessToken, id, code);

        return ResponseEntity.status(HttpStatus.OK).body(ride);
    }
}
