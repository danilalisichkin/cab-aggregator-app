package com.cabaggregator.promocodeservice.controller.doc;

import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoCodeSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Promo Code API Controller", description = "Provides get, create, update operations above promo codes")
public interface PromoCodeControllerDoc {

    @Operation(
            summary = "Get page",
            description = "Allows to get page of existing promo codes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
    })
    ResponseEntity<PageDto<PromoCodeDto>> getPageOfPromoCodes(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive @Max(20) Integer limit,
            @RequestParam(defaultValue = "value") PromoCodeSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get",
            description = "Allows to get existing promo code by its value")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: promo code does not exist")
    })
    ResponseEntity<PromoCodeDto> getPromoCode(
            @Parameter(
                    description = "Value of promo code")
            @PathVariable @Size(min = 2, max = 20) String code);

    @Operation(
            summary = "Create",
            description = "Allows to add/save new promo code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409", description = "Conflict: promo code with provided data already exists")
    })
    ResponseEntity<PromoCodeDto> createPromoCode(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new promo code",
                    required = true)
            @RequestBody @Valid PromoCodeAddingDto addingDto);

    @Operation(
            summary = "Update",
            description = "Allows to update existing promo code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: updatable promo code does not exist")
    })
    ResponseEntity<PromoCodeDto> updatePromoCode(
            @Parameter(
                    description = "Value of promo code")
            @PathVariable @Size(min = 2, max = 20) String code,
            @Parameter(
                    name = "Required data",
                    description = "Promo code data that will be changed",
                    required = true)
            @RequestBody @Valid PromoCodeUpdatingDto updatingDto);
}
