package com.cabaggregator.rideservice.controller.api;

import com.cabaggregator.rideservice.core.dto.promo.PromoCodeAddingDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeDto;
import com.cabaggregator.rideservice.core.dto.promo.PromoCodeUpdatingDto;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/promo-codes")
public class PromoCodeController {
    @GetMapping("/{id}")
    public ResponseEntity<PromoCodeDto> getPromoCode(@PathVariable ObjectId id) {

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<PromoCodeDto> createPromoCode(@RequestBody PromoCodeAddingDto dto) {

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromoCodeDto> updatePromoCode(
            @PathVariable ObjectId id,
            @RequestBody PromoCodeUpdatingDto updatingDto) {

        return ResponseEntity.ok().build();
    }
}
