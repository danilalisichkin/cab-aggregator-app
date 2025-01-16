package com.cabaggregator.ratingservice.controller.doc;

import com.cabaggregator.ratingservice.core.dto.driver.DriverRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateDto;
import com.cabaggregator.ratingservice.core.dto.driver.DriverRateSettingDto;
import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.enums.sort.DriverRateSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@SecurityRequirement(name = "token")
@Tag(name = "Driver Rate API Controller",
        description = "Provides get, create, update operations above driver rates")
public interface DriverRateControllerDoc {

    @Operation(
            summary = "Get average rating",
            description = "Allows to get driver rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: driver was never rated and has no rating")
    })
    ResponseEntity<Double> getDriverRating(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID driverId);

    @Operation(
            summary = "Get page",
            description = "Allows to get page of existing driver rates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403", description = "Forbidden: driver tried to get rates of another driver"),
    })
    ResponseEntity<PageDto<DriverRateDto>> getPageOfDriverRates(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID driverId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "id") DriverRateSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get",
            description = "Allows to get existing driver rate of specified ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403", description = "Forbidden: driver tried to get rate of another driver"),
            @ApiResponse(responseCode = "404", description = "Not found: driver rate from provided ride does not exist"),
    })
    ResponseEntity<DriverRateDto> getDriverRate(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID driverId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId rideId);

    @Operation(
            summary = "Create",
            description = "Allows to add/save new driver rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409", description = "Conflict: passenger rate with provided data already exists")
    })
    ResponseEntity<DriverRateDto> saveDriverRate(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new driver rate")
            @RequestBody @Valid DriverRateAddingDto addingDto);

    @Operation(
            summary = "Set rate",
            description = "Allows passenger to rate driver after the ride is completed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description =
                    "Bad request: invalid parameters or missing required fields; driver rate is already set"),
            @ApiResponse(responseCode = "404", description = "Not found: driver rate does not exist")
    })
    ResponseEntity<DriverRateDto> setDriverRate(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID driverId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId rideId,
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to set driver rate")
            @RequestBody @Valid DriverRateSettingDto settingDto);
}
