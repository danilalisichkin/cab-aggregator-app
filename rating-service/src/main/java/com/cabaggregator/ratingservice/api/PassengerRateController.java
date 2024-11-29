package com.cabaggregator.ratingservice.api;

import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.core.enums.sort.PassengerRateSortField;
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
@RequestMapping("/api/v1/rates/passenger")
public class PassengerRateController {

    @GetMapping("/{passengerId}")
    ResponseEntity<PageDto<PassengerRateDto>> getPageOfPassengerRates(
            @PathVariable UUID passengerId,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "id") PassengerRateSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{passengerId}/ride/{rideId}")
    ResponseEntity<PassengerRateDto> getPassengerRate(
            @PathVariable UUID passengerId,
            @PathVariable ObjectId rideId) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    ResponseEntity<PassengerRateDto> savePassengerRate(@RequestBody PassengerRateAddingDto addingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{passengerId}/ride/{rideId}")
    ResponseEntity<PassengerRateDto> setPassengerRate(
            @PathVariable UUID passengerId,
            @PathVariable ObjectId rideId,
            @RequestBody PassengerRateSettingDto settingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
