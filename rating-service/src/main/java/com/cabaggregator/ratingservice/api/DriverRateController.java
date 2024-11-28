package com.cabaggregator.ratingservice.api;

import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateSettingDto;
import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.enums.sort.DriverRateSortField;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rates/driver")
public class DriverRateController {

    @GetMapping("/{driverId}")
    ResponseEntity<PageDto<DriverRateDto>> getPageOfDriverRates(
            @PathVariable UUID driverId,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "id") DriverRateSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{driverId}/ride/{rideId}")
    ResponseEntity<DriverRateDto> getDriverRate(
            @PathVariable UUID driverId,
            @PathVariable ObjectId rideId) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    ResponseEntity<DriverRateDto> saveDriverRate(@RequestBody DriverRateAddingDto addingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{driverId}/ride/{rideId}")
    ResponseEntity<DriverRateDto> setDriverRate(
            @PathVariable UUID driverId,
            @PathVariable ObjectId rideId,
            @RequestBody DriverRateSettingDto settingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
