package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.MessageKeys;
import com.cabaggregator.rideservice.core.constant.ValidationRegex;
import com.cabaggregator.rideservice.core.dto.page.PagedDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingAddingDto;
import com.cabaggregator.rideservice.core.dto.ride.booking.RideBookingUpdatingDto;
import com.cabaggregator.rideservice.core.dto.ride.promo.RidePromoCodeDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateSettingDto;
import com.cabaggregator.rideservice.entity.enums.RideStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    public ResponseEntity<RideDto> getRide(@NotNull @PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/rate")
    public ResponseEntity<RideRateDto> getRideRate(@NotNull @PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/promo-code")
    public ResponseEntity<RidePromoCodeDto> getRidePromoCode(@NotNull @PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PagedDto<RideDto>> getPageOfRides(
            @Positive @RequestParam(required = false, name = "page", defaultValue = "1") int pageNumber,
            @Positive @RequestParam(required = false, name = "size", defaultValue = "10") int pageSize,
            @RequestParam(required = false, name = "status") RideStatus status,
            @RequestParam(required = false, name = "passenger-id") Long passengerId,
            @RequestParam(required = false, name = "driver-id") Long driverId,
            @Pattern(regexp = ValidationRegex.SORT_ORDER,
                    message = MessageKeys.ValidationErrors.VALIDATION_INVALID_SORT_ORDER)
            @RequestParam(required = false, name = "order", defaultValue = "desc") String sortOrder) {

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<RideDto> createRideBooking(
            @Valid @RequestBody RideBookingAddingDto addingDto) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/rate")
    public ResponseEntity<RideRateDto> setRideRate(
            @NotNull @PathVariable ObjectId id,
            @Valid @RequestBody RideRateSettingDto rate) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideDto> updateRideBooking(
            @NotNull @PathVariable ObjectId id,
            @Valid @RequestBody RideBookingUpdatingDto updatingDto) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RideDto> updateRide(
            @NotNull @PathVariable ObjectId id,
            @Valid @RequestBody RideUpdatingDto updatingDto) {

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RideDto> changeRideStatus(
            @NotNull @PathVariable ObjectId id,
            @NotNull @RequestBody RideStatus status) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/promo-code")
    public ResponseEntity<RidePromoCodeDto> setRidePromoCode(
            @NotNull @PathVariable ObjectId id,
            @NotNull @Size(min = 2, max = 50) @RequestBody String code) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/promo-code")
    public ResponseEntity<Void> cancelRidePromoCode(@NotNull @PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }
}
