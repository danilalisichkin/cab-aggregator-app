package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.constant.ValidationRegex;
import com.cabaggregator.rideservice.core.dto.page.PagedDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.order.RideOrderUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.enums.sort.RideSort;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("api/v1/rides")
public class RideController {
    @GetMapping
    public ResponseEntity<PagedDto<RideDto>> getPageOfRides(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestParam(name = "offset") @Positive Integer offset,
            @RequestParam(name = "limit") @Positive Integer limit,
            @Pattern(regexp = ValidationRegex.SORT_ORDER,
                    message = ValidationErrors.INVALID_SORT_ORDER)
            @RequestParam(name = "sort") RideSort sort,
            @RequestParam(name = "status") RideStatus status) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getRide(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @NotNull @PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/rate")
    public ResponseEntity<RideRateDto> getRideRate(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @NotNull @PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<RideDto> createRideOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestBody @Valid RideOrderAddingDto addingDto) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<RideRateDto> setRideRate(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id,
            @RequestBody @NotNull @Min(1) @Max(5) Integer rate) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideDto> updateRideOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id,
            @RequestBody @Valid RideOrderUpdatingDto updatingDto) {

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RideDto> changeRideStatus(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/promo-code")
    public ResponseEntity<RideDto> setRidePromoCode(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id,
            @RequestBody @NotNull @Size(min = 2, max = 50) String code) {

        return ResponseEntity.ok().build();
    }
}
