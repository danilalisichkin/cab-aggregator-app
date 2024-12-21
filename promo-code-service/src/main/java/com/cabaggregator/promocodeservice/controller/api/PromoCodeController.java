package com.cabaggregator.promocodeservice.controller.api;

import com.cabaggregator.promocodeservice.controller.doc.PromoCodeControllerDoc;
import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeDto;
import com.cabaggregator.promocodeservice.core.dto.promo.code.PromoCodeUpdatingDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoCodeSortField;
import com.cabaggregator.promocodeservice.service.PromoCodeService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/promo-codes")
public class PromoCodeController implements PromoCodeControllerDoc {

    private final PromoCodeService promoCodeService;

    @GetMapping
    public ResponseEntity<PageDto<PromoCodeDto>> getPageOfPromoCodes(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive @Max(20) Integer limit,
            @RequestParam(defaultValue = "value") PromoCodeSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        PageDto<PromoCodeDto> promoCodes = promoCodeService.getPageOfPromoCodes(offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(promoCodes);
    }

    @GetMapping("/{code}")
    public ResponseEntity<PromoCodeDto> getPromoCode(
            @PathVariable @Size(min = 2, max = 20) String code) {

        PromoCodeDto promoCode = promoCodeService.getPromoCode(code);

        return ResponseEntity.status(HttpStatus.OK).body(promoCode);
    }

    @PostMapping
    public ResponseEntity<PromoCodeDto> createPromoCode(
            @RequestBody PromoCodeAddingDto addingDto) {

        PromoCodeDto promoCode = promoCodeService.savePromoCode(addingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(promoCode);
    }

    @PutMapping("/{code}")
    public ResponseEntity<PromoCodeDto> updatePromoCode(
            @PathVariable @Size(min = 2, max = 20) String code,
            @RequestBody PromoCodeUpdatingDto updatingDto) {

        PromoCodeDto promoCode = promoCodeService.updatePromoCode(code, updatingDto);

        return ResponseEntity.status(HttpStatus.OK).body(promoCode);
    }
}
