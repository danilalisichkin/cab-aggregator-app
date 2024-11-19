package com.cabaggregator.passengerservice.controller.api;

import com.cabaggregator.passengerservice.controller.api.doc.PassengerControllerDocumentation;
import com.cabaggregator.passengerservice.core.constant.ValidationErrors;
import com.cabaggregator.passengerservice.core.dto.PagedDto;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSort;
import com.cabaggregator.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController implements PassengerControllerDocumentation {
    private final PassengerService passengerService;

    @Override
    @GetMapping
    public ResponseEntity<PagedDto<PassengerDto>> getPageOfPassengers(
            @RequestParam(name = "offset") @PositiveOrZero Integer offset,
            @RequestParam(name = "limit") @Positive Integer limit,
            @RequestParam(name = "sort", defaultValue = "id") PassengerSort sort) {

        log.info("Sending page of passengers");

        PagedDto<PassengerDto> page = passengerService.getPageOfPassengers(offset, limit, sort);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getPassenger(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id) {

        log.info("Getting passenger with id={}", id);

        PassengerDto passenger = passengerService.getPassengerById(id);

        return ResponseEntity.status(HttpStatus.OK).body(passenger);
    }

    @Override
    @PostMapping
    public ResponseEntity<PassengerDto> savePassenger(
            @RequestBody @Valid PassengerAddingDto passengerAddingDto) {

        log.info("Saving passenger with phone={}", passengerAddingDto.phoneNumber());

        PassengerDto passenger = passengerService.savePassenger(passengerAddingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(passenger);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDto> updatePassenger(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,
            @RequestBody @Valid PassengerUpdatingDto passengerDto) {

        log.info("Updating passenger with id={}", id);

        PassengerDto passenger = passengerService.updatePassenger(id, passengerDto);

        return ResponseEntity.status(HttpStatus.OK).body(passenger);
    }

    @Override
    @PutMapping("/{id}/rating")
    public ResponseEntity<PassengerDto> updatePassengerRating(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,
            @RequestBody @NotNull
            @Min(value = 0, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
            @Max(value = 5, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
            Double rating) {

        log.info("Updating rating of passenger with id={}", id);

        PassengerDto passenger = passengerService.updatePassengerRating(id, rating);

        return ResponseEntity.status(HttpStatus.OK).body(passenger);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id) {

        log.info("Deleting passenger with id={}", id);

        passengerService.deletePassengerById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
