package com.cabaggregator.passengerservice.controller.api;

import com.cabaggregator.passengerservice.controller.api.doc.PassengerControllerDocumentation;
import com.cabaggregator.passengerservice.core.constant.MessageKeys;
import com.cabaggregator.passengerservice.core.dto.PagedDto;
import com.cabaggregator.passengerservice.core.dto.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.exception.BadRequestException;
import com.cabaggregator.passengerservice.service.IPassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final IPassengerService passengerService;

    @Override
    @GetMapping
    public ResponseEntity<PagedDto<PassengerDto>> getPageOfPassengers(
            @Positive @RequestParam(required = false, name = "page", defaultValue = "1") int pageNumber,
            @Positive @RequestParam(required = false, name = "size", defaultValue = "10") int pageSize,
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortField,
            @RequestParam(required = false, name = "order", defaultValue = "desc") String sortOrder) {

        log.info("Sending page of passengers");

        if (!sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")) {
            throw new BadRequestException(MessageKeys.ERROR_INVALID_SORT_ORDER);
        }

        PagedDto<PassengerDto> page = passengerService.getPageOfPassengers(pageNumber, pageSize, sortField, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getPassenger(@NotNull @PathVariable long id) {
        log.info("Getting passenger with id={}", id);

        PassengerDto passenger = passengerService.getPassengerById(id);

        return ResponseEntity.status(HttpStatus.OK).body(passenger);
    }

    @Override
    @PostMapping
    public ResponseEntity<PassengerDto> savePassenger(@Valid @RequestBody PassengerAddingDto passengerAddingDto) {
        log.info("Saving passenger with phone={}", passengerAddingDto.phoneNumber());

        PassengerDto passenger = passengerService.savePassenger(passengerAddingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(passenger);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<PassengerDto> updatePassenger(
            @NotNull @PathVariable long id,
            @Valid @RequestBody PassengerUpdatingDto passengerDto) {

        log.info("Updating passenger with id={}", id);

        PassengerDto passenger = passengerService.updatePassenger(id, passengerDto);

        return ResponseEntity.status(HttpStatus.OK).body(passenger);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassengerById(@NotNull @PathVariable long id) {
        log.info("Deleting passenger with id={}", id);

        passengerService.deletePassengerById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
