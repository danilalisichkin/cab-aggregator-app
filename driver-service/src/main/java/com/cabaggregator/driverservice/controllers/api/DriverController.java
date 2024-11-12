package com.cabaggregator.driverservice.controllers.api;

import com.cabaggregator.driverservice.core.constant.MessageKeys;
import com.cabaggregator.driverservice.core.constant.ValidationRegex;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.service.IDriverService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final IDriverService driverService;

    @GetMapping
    public ResponseEntity<PagedDto<DriverDto>> getPageOfDrivers(
            @Positive @RequestParam(required = false, name = "page", defaultValue = "1") int pageNumber,
            @Positive @RequestParam(required = false, name = "size", defaultValue = "10") int pageSize,
            @Pattern(regexp = ValidationRegex.SORT_ORDER,
                    message = MessageKeys.ValidationErrors.INVALID_SORT_ORDER)
            @RequestParam(required = false, name = "sort", defaultValue = "id") String sortField,
            @RequestParam(required = false, name = "order", defaultValue = "desc") String sortOrder) {

        log.info("Sending page of drivers");

        PagedDto<DriverDto> page =
                driverService.getPageOfDrivers(pageNumber, pageSize, sortField, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriver(@NotNull @PathVariable Long id) {
        log.info("Sending driver with id={}", id);

        DriverDto driver = driverService.getDriverById(id);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @PostMapping
    public ResponseEntity<DriverDto> saveDriver(@Valid @RequestBody DriverAddingDto driverAddingDTO) {
        log.info("Saving driver with phone={}", driverAddingDTO.phoneNumber());

        DriverDto driver = driverService.saveDriver(driverAddingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(driver);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDto> updateDriver(
            @NotNull @PathVariable Long id,
            @Valid @RequestBody DriverUpdatingDto driverDto) {

        log.info("Updating driver with id={}", id);

        DriverDto driver = driverService.updateDriver(id, driverDto);

        return ResponseEntity.status(HttpStatus.OK).body(driver);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@NotNull @PathVariable Long id) {
        log.info("Deleting driver with id={}", id);

        driverService.deleteDriverById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
