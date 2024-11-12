package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.dto.page.PagedDto;
import com.cabaggregator.rideservice.core.dto.ride.rate.RideRateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/ride-rates")
public class RideRateController {
    @GetMapping
    public ResponseEntity<PagedDto<RideRateDto>> getPageOfRideRates(
            @RequestParam(required = false, name = "passenger-id") Long passengerId,
            @RequestParam(required = false, name = "driver-id") Long driverId) {

        return ResponseEntity.ok().build();
    }
}
