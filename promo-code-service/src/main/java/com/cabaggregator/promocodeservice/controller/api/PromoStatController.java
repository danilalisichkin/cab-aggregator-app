package com.cabaggregator.promocodeservice.controller.api;

import com.cabaggregator.promocodeservice.controller.doc.PromoStatControllerDoc;
import com.cabaggregator.promocodeservice.core.dto.page.PageDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatAddingDto;
import com.cabaggregator.promocodeservice.core.dto.promo.stat.PromoStatDto;
import com.cabaggregator.promocodeservice.core.enums.sort.PromoStatSortField;
import com.cabaggregator.promocodeservice.service.PromoStatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/promo-stats")
public class PromoStatController implements PromoStatControllerDoc {

    private final PromoStatService promoStatService;

    @GetMapping
    public ResponseEntity<PageDto<PromoStatDto>> getPageOfPromoStats(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive @Max(20) Integer limit,
            @RequestParam(defaultValue = "id") PromoStatSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        PageDto<PromoStatDto> promoStats = promoStatService.getPageOfPromoStats(offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(promoStats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromoStatDto> getPromoStat(@PathVariable Long id) {
        PromoStatDto promoStat = promoStatService.getPromoStat(id);

        return ResponseEntity.status(HttpStatus.OK).body(promoStat);
    }

    @PostMapping
    public ResponseEntity<PromoStatDto> createPromoStat(
            @RequestBody @Valid PromoStatAddingDto addingDto) {

        PromoStatDto promoStat = promoStatService.savePromoStat(addingDto);

        return ResponseEntity.status(HttpStatus.OK).body(promoStat);
    }
}
