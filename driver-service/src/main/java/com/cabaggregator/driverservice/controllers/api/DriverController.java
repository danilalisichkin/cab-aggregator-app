package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.controllers.api.doc.DriverControllerDocumentation;
import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSort;
import com.cabaggregator.driverservice.service.DriverService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController implements DriverControllerDocumentation {
    private final DriverService driverService;

    @Override
    @GetMapping
    public ResponseEntity<PagedDto<DriverDto>> getPageOfDrivers(
            @RequestParam(name = "offset")
            @Min(value = 0, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
            Integer offset,
            @RequestParam(name = "limit") @Positive Integer limit,
            @RequestParam(name = "sort") DriverSort sort) {

        log.info("Sending page of drivers");

        PagedDto<DriverDto> page =
                driverService.getPageOfDrivers(offset, limit, sort);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriver(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id) {

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
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,
            @RequestBody @Valid DriverUpdatingDto driverDto) {

        log.info("Updating driver with id={}", id);

        DriverDto driver = driverService.updateDriver(id, driverDto);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @Override
    @PutMapping("/{id}/rating")
    public ResponseEntity<DriverDto> updateDriverRating(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,
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
    public ResponseEntity<Void> deleteDriver(
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id) {

        log.info("Deleting driver with id={}", id);

        driverService.deleteDriverById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
