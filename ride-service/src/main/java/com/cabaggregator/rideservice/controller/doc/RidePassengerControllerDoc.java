package com.cabaggregator.rideservice.controller.doc;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.dto.ride.RideUpdatingDto;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
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
@Tag(name = "Ride Passenger API Controller",
        description = "Provides operations above passenger rides")
public interface RidePassengerControllerDoc {

    @Operation(
            summary = "Get page",
            description = "Allows to get page of existing passenger rides")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403", description = "Forbidden: passenger tried to get rides of another passenger"),
    })
    ResponseEntity<PageDto<RideDto>> getPageOfPassengerRides(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID passengerId,
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE)
            @Max(value = 40, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") RideSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) RideStatus status);

    @Operation(
            summary = "Get",
            description = "Allows to get existing passenger ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403", description = "Forbidden: passenger tried to get ride of another passenger"),
            @ApiResponse(responseCode = "404", description = "Not found: ride does not exist"),
    })
    ResponseEntity<RideDto> getRide(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID passengerId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId id);

    @Operation(
            summary = "Change ride",
            description = "Allows to change data of the ride before it started")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400",
                    description = "Bad request: invalid parameters or missing required fields; ride is already requested"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden: passenger tried to change ride of another passenger; "
                            + "passenger tried to change ride status incorrectly"),
            @ApiResponse(responseCode = "404", description = "Not found: ride does not exist"),
    })
    ResponseEntity<RideDto> updateRide(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID passengerId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId id,
            @Parameter(
                    name = "Required data",
                    description = "Data that will be updated",
                    required = true)
            @RequestBody @Valid RideUpdatingDto updatingDto);

    @Operation(
            summary = "Change status",
            description = "Allows to change payment status of existing passenger ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden: passenger tried to change ride of another passenger; "
                            + "passenger tried to change ride status incorrectly"),
            @ApiResponse(responseCode = "404", description = "Not found: ride does not exist"),
    })
    ResponseEntity<RideDto> changeRideStatus(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID passengerId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId id,
            @Parameter(
                    name = "New status",
                    description = "Status that will be set",
                    required = true)
            @RequestBody RideStatus status);
}