package com.cabaggregator.paymentservice.controller.api;

import com.cabaggregator.paymentservice.core.constant.ValidationErrors;
import com.cabaggregator.paymentservice.core.dto.page.PageDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.enums.sort.PaymentAccountSortField;
import com.cabaggregator.paymentservice.service.PaymentAccountService;
import jakarta.validation.Valid;
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

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment-accounts")
public class PaymentAccountController {

    private final PaymentAccountService paymentAccountService;

    @GetMapping
    public ResponseEntity<PageDto<PaymentAccountDto>> getPageOfPaymentAccounts(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE) Integer limit,
            @RequestParam(defaultValue = "createdAt") PaymentAccountSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        PageDto<PaymentAccountDto> page =
                paymentAccountService.getPageOfPaymentAccounts(offset, limit, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentAccountDto> getPaymentAccount(@PathVariable UUID id) {
        PaymentAccountDto account = paymentAccountService.getPaymentAccount(id);

        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @GetMapping("/{id}/payment-cards")
    public ResponseEntity<List<PaymentCardDto>> getPaymentCards(@PathVariable UUID id) {
        List<PaymentCardDto> paymentCards = paymentAccountService.getAccountPaymentCards(id);

        return ResponseEntity.status(HttpStatus.OK).body(paymentCards);
    }

    @GetMapping("/{id}/payment-cards/default")
    public ResponseEntity<PaymentCardDto> getDefaultPaymentCard(@PathVariable UUID id) {
        PaymentCardDto paymentCard = paymentAccountService.getAccountDefaultPaymentCard(id);

        return ResponseEntity.status(HttpStatus.OK).body(paymentCard);
    }

    @PostMapping
    public ResponseEntity<PaymentAccountDto> createPaymentAccount(
            @RequestBody @Valid PaymentAccountAddingDto addingDto) {

        PaymentAccountDto account = paymentAccountService.createPaymentAccount(addingDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(account);
    }

    @PostMapping("/{id}/payment-cards/default")
    public ResponseEntity<Void> setDefaultPaymentCard(
            @PathVariable UUID id,
            @RequestBody @Size(min = 1, max = 40, message = ValidationErrors.INVALID_STRING_LENGTH)
            String paymentMethodId) {

        paymentAccountService.setAccountDefaultPaymentCard(id, paymentMethodId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentAccountDto> updatePaymentAccount(
            @PathVariable UUID id,
            @RequestBody @Size(min = 1, max = 30, message = ValidationErrors.INVALID_STRING_LENGTH)
            String stripeAccountId) {

        PaymentAccountDto account = paymentAccountService.updatePaymentAccount(id, stripeAccountId);

        return ResponseEntity.status(HttpStatus.OK).body(account);
    }
}
