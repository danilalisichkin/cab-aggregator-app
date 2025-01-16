package com.cabaggregator.ratingservice.controller.api;

import com.cabaggregator.ratingservice.controller.doc.PassengerRateControllerDoc;
import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.core.enums.sort.PassengerRateSortField;
import com.cabaggregator.ratingservice.service.PassengerRateService;
import jakarta.validation.Valid;
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

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rates/passenger")
public class PassengerRateController implements PassengerRateControllerDoc {

    private final PassengerRateService passengerRateService;

    @Override
    @GetMapping("/{passengerId}/average")
    public ResponseEntity<Double> getPassengerRating(@PathVariable UUID passengerId) {
        Double rating = passengerRateService.getPassengerRating(passengerId);

        return ResponseEntity.status(HttpStatus.OK).body(rating);
    }

    @Override
    @GetMapping("/{passengerId}")
    public ResponseEntity<PageDto<PassengerRateDto>> getPageOfPassengerRates(
            @PathVariable UUID passengerId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "id") PassengerRateSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        PageDto<PassengerRateDto> rates =
                passengerRateService.getPageOfPassengerRates(passengerId, offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(rates);
    }

    @Override
    @GetMapping("/{passengerId}/ride/{rideId}")
    public ResponseEntity<PassengerRateDto> getPassengerRate(
            @PathVariable UUID passengerId,
            @PathVariable ObjectId rideId) {

        PassengerRateDto rate = passengerRateService.getPassengerRate(passengerId, rideId);

        return ResponseEntity.status(HttpStatus.OK).body(rate);
    }

    @Override
    @PostMapping
    public ResponseEntity<PassengerRateDto> savePassengerRate(
            @RequestBody @Valid PassengerRateAddingDto addingDto) {

        PassengerRateDto rate = passengerRateService.savePassengerRate(addingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(rate);
    }

    @Override
    @PutMapping("/{passengerId}/ride/{rideId}")
    public ResponseEntity<PassengerRateDto> setPassengerRate(
            @PathVariable UUID passengerId,
            @PathVariable ObjectId rideId,
            @RequestBody @Valid PassengerRateSettingDto settingDto) {

        PassengerRateDto rate = passengerRateService.setPassengerRate(passengerId, rideId, settingDto);

        return ResponseEntity.status(HttpStatus.OK).body(rate);
    }
}
