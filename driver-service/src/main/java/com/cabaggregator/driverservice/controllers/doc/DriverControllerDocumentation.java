package com.cabaggregator.driverservice.controllers.doc;

import com.cabaggregator.driverservice.core.constant.ValidationErrors;
import com.cabaggregator.driverservice.core.dto.driver.DriverAddingDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverDto;
import com.cabaggregator.driverservice.core.dto.driver.DriverUpdatingDto;
import com.cabaggregator.driverservice.core.dto.page.PageDto;
import com.cabaggregator.driverservice.core.enums.sort.DriverSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Driver API Controller", description = "Provides CRUD operations with drivers")
public interface DriverControllerDocumentation {

    @Operation(summary = "Get page", description = "Allows to get a page of existing drivers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
    })
    ResponseEntity<PageDto<DriverDto>> getPageOfDrivers(
            @Parameter(name = "offset", description = "The starting point of the page", required = true)
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,

            @Parameter(name = "limit", description = "The number of drivers to return", required = true)
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,

            @Parameter(name = "sort field", description = "Sorting criteria for the drivers")
            @RequestParam(defaultValue = "id") DriverSortField sortBy,

            @Parameter(name = "sort order", description = "Sorting criteria for the drivers")
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(summary = "Get driver", description = "Allows to get existing driver by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "404", description = "Driver not found: driver with ID does not exist")
    })
    ResponseEntity<DriverDto> getDriver(
            @Parameter(name = "id", description = "ID of the driver", required = true)
            @PathVariable UUID id);

    @Operation(summary = "Add/save driver", description = "Allows to add/save a new driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "404", description = "Car not found: car with ID does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict: phone number already belongs to another driver"),
            @ApiResponse(responseCode = "409", description = "Conflict: email already belongs to another driver"),
            @ApiResponse(responseCode = "409", description = "Conflict: car already belongs to another driver")
    })
    ResponseEntity<DriverDto> saveDriver(
            @RequestBody @Valid DriverAddingDto driverAddingDTO);

    @Operation(summary = "Update driver", description = "Allows to update existing driver by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "404", description = "Driver not found: driver with ID does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict: phone number already belongs to another driver"),
            @ApiResponse(responseCode = "409", description = "Conflict: email already belongs to another driver")
    })
    ResponseEntity<DriverDto> updateDriver(
            @Parameter(name = "id", description = "ID of the driver to be updated", required = true)
            @PathVariable UUID id,

            @RequestBody @Valid DriverUpdatingDto driverDto);

    @Operation(summary = "Update car ID", description = "Allows to update the car ID an existing driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Driver not found: driver with ID or car with ID does not exist")
    })
    ResponseEntity<DriverDto> updateDriverCar(
            @Parameter(
                    description = "ID of the driver whose car is to be updated")
            @PathVariable UUID id,
            @Parameter(
                    name = "Car ID",
                    description = "ID of the registered car is to be connected with driver",
                    required = true)
            @RequestBody @NotNull Long carId);

    @Operation(summary = "Update rating", description = "Allows to update the rating of an existing driver by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "404", description = "Driver not found: driver with ID does not exist")
    })
    ResponseEntity<DriverDto> updateDriverRating(
            @Parameter(name = "id", description = "ID of the driver whose rating is to be updated", required = true)
            @PathVariable UUID id,

            @Parameter(name = "rating", description = "New rating for the driver (0-5)", required = true)
            @RequestBody @NotNull
            @Min(value = 0, message = ValidationErrors.INVALID_NUMBER_MIN_VALUE)
            @Max(value = 5, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE)
            Double rating);

    @Operation(summary = "Delete driver", description = "Allows to delete existing driver by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters"),
            @ApiResponse(responseCode = "400", description = "Bad request: missing required fields"),
            @ApiResponse(responseCode = "404", description = "Driver not found: driver with ID does not exist")
    })
    ResponseEntity<Void> deleteDriver(
            @Parameter(name = "id", description = "ID of the driver to be deleted", required = true)
            @PathVariable UUID id);
}
