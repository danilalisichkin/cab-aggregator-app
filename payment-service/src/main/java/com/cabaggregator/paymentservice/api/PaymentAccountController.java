package com.cabaggregator.paymentservice.api;

import com.cabaggregator.paymentservice.core.dto.page.PageDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.enums.sort.PaymentAccountSortField;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment-accounts")
public class PaymentAccountController {

    @GetMapping
    public ResponseEntity<PageDto<PaymentAccountDto>> getPageOfPaymentAccounts(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "createdAt") PaymentAccountSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentAccountDto> getPaymentAccount(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/payment-cards")
    public ResponseEntity<List<PaymentCardDto>> getPaymentCards(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/payment-cards/default")
    public ResponseEntity<Void> getDefaultPaymentCard(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<PaymentAccountDto> createPaymentAccount(
            @RequestBody PaymentAccountAddingDto addingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/payment-cards/default")
    public ResponseEntity<Void> setDefaultPaymentCard(
            @PathVariable UUID id, @RequestBody String paymentMethodId) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentAccountDto> updatePaymentAccount(
            @PathVariable UUID id, @RequestBody String stripeAccountId) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
