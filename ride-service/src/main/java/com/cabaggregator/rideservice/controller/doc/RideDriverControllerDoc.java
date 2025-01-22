package com.cabaggregator.rideservice.controller.doc;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.ride.RideDto;
import com.cabaggregator.rideservice.core.enums.RidePaymentStatus;
import com.cabaggregator.rideservice.core.enums.RideStatus;
import com.cabaggregator.rideservice.core.enums.sort.RideSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
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
@Tag(name = "Ride Driver API Controller",
        description = "Provides operations above driver rides")
public interface RideDriverControllerDoc {

    @Operation(
            summary = "Get page",
            description = "Allows to get page of existing driver rides")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403", description = "Forbidden: driver tried to get rides of another driver"),
    })
    ResponseEntity<PageDto<RideDto>> getPageOfDriverRides(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID driverId,
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
            description = "Allows to get existing driver ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403", description = "Forbidden: driver tried to get ride of another driver"),
            @ApiResponse(responseCode = "404", description = "Not found: ride does not exist"),
    })
    ResponseEntity<RideDto> getRide(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID driverId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId id);

    @Operation(
            summary = "Change payment status",
            description = "Allows to change payment status of existing driver ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden: driver tried to change ride of another driver; "
                            + "driver tried to change payment status while payment method is set to card"),
            @ApiResponse(responseCode = "404", description = "Not found: ride does not exist"),
    })
    ResponseEntity<RideDto> changeRidePaymentStatus(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID driverId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId id,
            @Parameter(
                    name = "New payment status",
                    description = "Payment status that will be set",
                    required = true)
            @RequestBody @NotNull RidePaymentStatus paymentStatus);

    @Operation(
            summary = "Change status",
            description = "Allows to change payment status of existing driver ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden: driver tried to change ride of another driver; "
                            + "driver tried to change ride status incorrectly"),
            @ApiResponse(responseCode = "404", description = "Not found: ride does not exist"),
    })
    ResponseEntity<RideDto> changeRideStatus(
            @Parameter(
                    description = "Driver identifier")
            @PathVariable UUID driverId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId id,
            @Parameter(
                    name = "New status",
                    description = "Status that will be set",
                    required = true)
            @RequestBody RideStatus status);
}
