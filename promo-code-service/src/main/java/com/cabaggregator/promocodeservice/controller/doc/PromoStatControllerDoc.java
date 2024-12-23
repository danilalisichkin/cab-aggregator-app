package com.cabaggregator.promocodeservice.controller.doc;

import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoStatSortField;
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

@Tag(name = "Promo Stat API Controller", description = "Provides get, create operations above promo code stats")
public interface PromoStatControllerDoc {

    @Operation(
            summary = "Get page",
            description = "Allows to get page of existing promo code stats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
    })
    ResponseEntity<PageDto<PromoStatDto>> getPageOfPromoStats(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive @Max(20) Integer limit,
            @RequestParam(defaultValue = "id") PromoStatSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get",
            description = "Allows to get existing promo code stat by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: promo code stat does not exist")
    })
    ResponseEntity<PromoStatDto> getPromoStat(
            @Parameter(
                    description = "Id of promo stat")
            @PathVariable Long id);

    @Operation(
            summary = "Create",
            description = "Allows to add/save new promo code stat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409", description = "Conflict: promo code stat with provided data already exists")
    })
    ResponseEntity<PromoStatDto> createPromoStat(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new promo code stat",
                    required = true)
            @RequestBody @Valid PromoStatAddingDto addingDto);
}
