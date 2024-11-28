package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.controllers.api.doc.DriverControllerDocumentation;
import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSortField;
import com.cabaggregator.driverservice.service.DriverService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController implements DriverControllerDocumentation {
    private final DriverService driverService;

    @Override
    @GetMapping
    public ResponseEntity<PageDto<DriverDto>> getPageOfDrivers(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") DriverSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        log.info("Sending page of drivers");

        PageDto<DriverDto> page = driverService.getPageOfDrivers(offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable UUID id) {
        log.info("Sending driver with id={}", id);

        DriverDto driver = driverService.getDriverById(id);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @Override
    @PostMapping
    public ResponseEntity<DriverDto> saveDriver(@RequestBody @Valid DriverAddingDto driverAddingDTO) {
        log.info("Saving driver with phone={}", driverAddingDTO.phoneNumber());

        DriverDto driver = driverService.saveDriver(driverAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<DriverDto> updateDriver(
            @PathVariable UUID id,
            @RequestBody @Valid DriverUpdatingDto driverDto) {

        log.info("Updating driver with id={}", id);

        DriverDto driver = driverService.updateDriver(id, driverDto);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @Override
    @PutMapping("/{id}/rating")
    public ResponseEntity<DriverDto> updateDriverRating(
            @PathVariable UUID id,
            @RequestBody @NotNull
            @Min(value = 0, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
            @Max(value = 5, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
            Double rating) {

        log.info("Updating rating of driver with id={}", id);

        DriverDto driver = driverService.updateDriverRating(id, rating);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable UUID id) {
        log.info("Deleting driver with id={}", id);

        driverService.deleteDriverById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
