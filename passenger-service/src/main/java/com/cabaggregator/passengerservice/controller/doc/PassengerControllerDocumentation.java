package com.cabaggregator.passengerservice.controller.doc;

import com.cabaggregator.passengerservice.core.constant.ValidationErrors;
import com.cabaggregator.passengerservice.core.dto.page.PageDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerAddingDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerDto;
import com.cabaggregator.passengerservice.core.dto.passenger.PassengerUpdatingDto;
import com.cabaggregator.passengerservice.core.enums.sort.PassengerSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "Passenger API Controller", description = "Provides CRUD-operations with passengers")
public interface PassengerControllerDocumentation {

    @Operation(
            summary = "Get page",
            description = "Allows to get page of existing passengers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields")
    })
    ResponseEntity<PageDto<PassengerDto>> getPageOfPassengers(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") PassengerSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get",
            description = "Allows to get existing passenger by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: passenger does not exist")
    })
    ResponseEntity<PassengerDto> getPassenger(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID id);

    @Operation(
            summary = "Create",
            description = "Allows to create new passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409", description = "Conflict: passenger with provided data already exists")
    })
    ResponseEntity<PassengerDto> createPassenger(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new passenger")
            @RequestBody @Valid PassengerAddingDto passengerAddingDto);

    @Operation(
            summary = "Update",
            description = "Allows to update existing passenger by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: passenger does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict: passenger with provided unique data already exists")
    })
    ResponseEntity<PassengerDto> updatePassenger(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID id,
            @Parameter(
                    name = "Required data",
                    description = "Passenger data that will be updated")
            @RequestBody @Valid PassengerUpdatingDto passengerDto);

    @Operation(
            summary = "Delete",
            description = "Allows to delete existing passenger by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: passenger does not exist")
    })
    ResponseEntity<Void> deletePassenger(
            @Parameter(
                    description = "Passenger identifier")
            @PathVariable UUID id);
}
