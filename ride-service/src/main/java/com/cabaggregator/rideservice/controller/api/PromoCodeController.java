package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.constant.ValidationRegex;
import com.cabaggregator.rideservice.core.dto.page.PagedDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import com.cabaggregator.rideservice.core.enums.sort.PromoCodeSort;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.bson.types.ObjectId;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/v1/promo-codes")
public class PromoCodeController {
    @GetMapping
    public ResponseEntity<PagedDto<PromoCodeDto>> getPageOfPromoCodes(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestParam(name = "offset") @Positive Integer offset,
            @RequestParam(name = "limit") @Positive Integer limit,
            @Pattern(regexp = ValidationRegex.SORT_ORDER,
                    message = ValidationErrors.INVALID_SORT_ORDER)
            @RequestParam(name = "sort") PromoCodeSort sort) {

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<PromoCodeDto> getPromoCode(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull String code) {

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<PromoCodeDto> createPromoCode(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestBody @Valid PromoCodeAddingDto promoCodeDto) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromoCodeDto> updatePromoCode(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotNull ObjectId id,
            @RequestBody @Valid PromoCodeUpdatingDto updatingDto) {

        return ResponseEntity.ok().build();
    }
}
