package com.cabaggregator.ratingservice.controller.doc;

import com.cabaggregator.ratingservice.core.dto.page.PageDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateAddingDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateDto;
import com.cabaggregator.ratingservice.core.dto.passenger.PassengerRateSettingDto;
import com.cabaggregator.ratingservice.core.enums.sort.PassengerRateSortField;
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
@Tag(name = "Passenger Rate API Controller",
        description = "Provides get, create, update operations above passenger rates")
public interface PassengerRateControllerDoc {

    @Operation(
            summary = "Get average rating",
            description = "Allows to get passenger rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: passenger was never rated and has no rating")
    })
    ResponseEntity<Double> getPassengerRating(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID passengerId);

    @Operation(
            summary = "Get page",
            description = "Allows to get page of existing passenger rates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403", description = "Forbidden: passenger tried to get rates of another passenger"),
    })
    ResponseEntity<PageDto<PassengerRateDto>> getPageOfPassengerRates(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID passengerId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "id") PassengerRateSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get",
            description = "Allows to get existing passenger rate of specified ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "403", description = "Forbidden: passenger tried to get rate of another passenger"),
            @ApiResponse(responseCode = "404", description = "Not found: passenger rate from provided ride does not exist"),
    })
    ResponseEntity<PassengerRateDto> getPassengerRate(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID passengerId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId rideId);

    @Operation(
            summary = "Create",
            description = "Allows to add/save new passenger rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409", description = "Conflict: passenger rate with provided data already exists")
    })
    ResponseEntity<PassengerRateDto> savePassengerRate(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new passenger rate")
            @RequestBody @Valid PassengerRateAddingDto addingDto);

    @Operation(
            summary = "Set rate",
            description = "Allows driver to rate passenger after the ride is completed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description =
                    "Bad request: invalid parameters or missing required fields; passenger rate is already set"),
            @ApiResponse(responseCode = "404", description = "Not found: passenger rate does not exist")
    })
    ResponseEntity<PassengerRateDto> setPassengerRate(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID passengerId,
            @Parameter(
                    description = "Ride identifier")
            @PathVariable ObjectId rideId,
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to set passenger rate")
            @RequestBody @Valid PassengerRateSettingDto settingDto);
}
