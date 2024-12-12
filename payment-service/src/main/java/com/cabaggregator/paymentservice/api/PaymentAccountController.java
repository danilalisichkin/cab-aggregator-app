package com.cabaggregator.paymentservice.api;

import com.cabaggregator.paymentservice.core.constant.ValidationErrors;
import com.cabaggregator.paymentservice.core.dto.page.PageDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.enums.sort.PaymentAccountSortField;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/payment-accounts")
public class PaymentAccountController {

    @GetMapping
    public ResponseEntity<PageDto<PaymentAccountDto>> getPageOfPaymentAccounts(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE) Integer limit,
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
            @RequestBody @Valid PaymentAccountAddingDto addingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/payment-cards/default")
    public ResponseEntity<Void> setDefaultPaymentCard(
            @PathVariable UUID id,
            @RequestBody @Size(min = 1, max = 40, message = ValidationErrors.INVALID_STRING_LENGTH)
            String paymentMethodId) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentAccountDto> updatePaymentAccount(
            @PathVariable UUID id,
            @RequestBody @Size(min = 1, max = 30, message = ValidationErrors.INVALID_STRING_LENGTH)
            String stripeAccountId) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
