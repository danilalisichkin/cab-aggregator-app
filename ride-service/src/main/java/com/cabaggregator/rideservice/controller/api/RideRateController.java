package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/ride-rates")
public class RideRateController {
    @GetMapping
    public ResponseEntity<List<RideRateDto>> getListOfRideRates(
            @RequestParam(required = false, name = "passenger-id")
            @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) @NotEmpty String passengerId,
            @RequestParam(required = false, name = "driver-id")
            @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) @NotEmpty String driverId) {

        return ResponseEntity.ok().build();
    }
}
