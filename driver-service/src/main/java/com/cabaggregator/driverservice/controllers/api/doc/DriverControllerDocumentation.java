package com.cabaggregator.driverservice.controllers.api.doc;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PagedDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Driver API Controller", description = "Provides CRUD operations with drivers")
public interface DriverControllerDocumentation {

    @Operation(summary = "Get page", description = "Allows to get a page of existing drivers")
    ResponseEntity<PagedDto<DriverDto>> getPageOfDrivers(
            @Parameter(name = "offset", description = "The starting point of the page", required = true)
            @RequestParam(name = "offset") @PositiveOrZero Integer offset,

            @Parameter(name = "limit", description = "The number of drivers to return", required = true)
            @RequestParam(name = "limit") @Positive Integer limit,

            @Parameter(name = "sort", description = "Sorting criteria for the drivers")
            @RequestParam(name = "sort") DriverSort sort);

    @Operation(summary = "Get driver by ID", description = "Allows to get existing driver by its ID")
    ResponseEntity<DriverDto> getDriver(
            @Parameter(name = "id", description = "ID of the driver", required = true)
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id);

    @Operation(summary = "Add/save a driver", description = "Allows to add/save a new driver")
    ResponseEntity<DriverDto> saveDriver(
            @RequestBody @Valid DriverAddingDto driverAddingDTO);

    @Operation(summary = "Update driver", description = "Allows to update existing driver by its ID")
    ResponseEntity<DriverDto> updateDriver(
            @Parameter(name = "id", description = "ID of the driver to be updated", required = true)
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,
            @RequestBody @Valid DriverUpdatingDto driverDto);

    @Operation(summary = "Update driver rating", description = "Allows to update the rating of an existing driver by its ID")
    ResponseEntity<DriverDto> updateDriverRating(
            @Parameter(name = "id", description = "ID of the driver whose rating is to be updated", required = true)
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id,

            @Parameter(name = "rating", description = "New rating for the driver (0-5)", required = true)
            @RequestBody @NotNull
            @Min(value = 0, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
            @Max(value = 5, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
            Double rating);

    @Operation(summary = "Delete driver", description = "Allows to delete existing driver by its ID")
    ResponseEntity<Void> deleteDriver(
            @Parameter(name = "id", description = "ID of the driver to be deleted", required = true)
            @PathVariable @NotEmpty @UUID(message = ValidationErrors.INVALID_UUID_FORMAT) String id);
}
