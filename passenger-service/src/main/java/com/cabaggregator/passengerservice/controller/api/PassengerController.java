package com.cabaggregator.passengerservice.controller.api;

import com.cabaggregator.passengerservice.controller.doc.PassengerControllerDocumentation;
import com.cabaggregator.passengerservice.core.constant.ValidationErrors;
import com.cabaggregator.passengerservice.core.dto.page.PageDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSortField;
import com.cabaggregator.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController implements PassengerControllerDocumentation {
    private final PassengerService passengerService;

    @Override
    @GetMapping
    public ResponseEntity<PageDto<PassengerDto>> getPageOfPassengers(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") PassengerSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        log.info("Get page of passengers");

        PageDto<PassengerDto> page = passengerService.getPageOfPassengers(offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getPassenger(@PathVariable UUID id) {
        log.info("Get passenger with id={}", id);

        PassengerDto passenger = passengerService.getPassengerById(id);

        return ResponseEntity.status(HttpStatus.OK).body(passenger);
    }

    @Override
    @PostMapping
    public ResponseEntity<PassengerDto> createPassenger(
            @RequestBody @Valid PassengerAddingDto passengerAddingDto) {

        log.info("Add passenger with data={}", passengerAddingDto);

        PassengerDto passenger = passengerService.savePassenger(passengerAddingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(passenger);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDto> updatePassenger(
            @PathVariable UUID id,
            @RequestBody @Valid PassengerUpdatingDto passengerDto) {

        log.info("Update passenger with id={} and data={}", id, passengerDto);

        PassengerDto passenger = passengerService.updatePassenger(id, passengerDto);

        return ResponseEntity.status(HttpStatus.OK).body(passenger);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable UUID id) {
        log.info("Delete passenger with id={}", id);

        passengerService.deletePassengerById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
