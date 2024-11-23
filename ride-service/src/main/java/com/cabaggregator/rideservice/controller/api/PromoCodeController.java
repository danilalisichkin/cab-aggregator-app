package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.constant.ValidationErrors;
import com.cabaggregator.rideservice.core.dto.page.PageDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import com.cabaggregator.rideservice.core.enums.sort.PromoCodeSortField;
import com.cabaggregator.rideservice.service.PromoCodeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
@RequestMapping("api/v1/promo-codes")
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    @GetMapping
    public ResponseEntity<PageDto<PromoCodeDto>> getPageOfPromoCodes(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive
            @Max(value = 20, message = ValidationErrors.INVALID_NUMBER_MAX_VALUE) Integer limit,
            @RequestParam(defaultValue = "id") PromoCodeSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        PageDto<PromoCodeDto> page = promoCodeService.getPageOfPromoCodes(accessToken, offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{code}")
    public ResponseEntity<PromoCodeDto> getPromoCode(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotEmpty
            @Size(min = 2, max = 50, message = ValidationErrors.INVALID_STRING_LENGTH) String code) {

        PromoCodeDto promoCode = promoCodeService.getPromoCodeByValueSecured(accessToken, code);

        return ResponseEntity.status(HttpStatus.OK).body(promoCode);
    }

    @PostMapping
    public ResponseEntity<PromoCodeDto> createPromoCode(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestBody @Valid PromoCodeAddingDto promoCodeDto) {

        PromoCodeDto promoCode = promoCodeService.savePromoCode(accessToken, promoCodeDto);

        return ResponseEntity.status(HttpStatus.OK).body(promoCode);
    }

    @PutMapping("/{code}")
    public ResponseEntity<PromoCodeDto> updatePromoCode(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @PathVariable @NotEmpty
            @Size(min = 2, max = 50, message = ValidationErrors.INVALID_STRING_LENGTH) String code,
            @RequestBody @Valid PromoCodeUpdatingDto updatingDto) {

        PromoCodeDto promoCode = promoCodeService.updatePromoCode(accessToken, code, updatingDto);

        return ResponseEntity.status(HttpStatus.OK).body(promoCode);
    }
}
