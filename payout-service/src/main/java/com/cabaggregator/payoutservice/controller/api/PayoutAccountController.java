package com.cabaggregator.payoutservice.controller.api;

import com.cabaggregator.payoutservice.core.constant.ValidationErrors;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountAddingDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountDto;
import com.cabaggregator.payoutservice.core.dto.page.PageDto;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.core.enums.sort.BalanceOperationSortField;
import com.cabaggregator.payoutservice.core.enums.sort.PayoutAccountSortField;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/v1/payout-accounts")
public class PayoutAccountController {
    @GetMapping
    public ResponseEntity<PageDto<PayoutAccountDto>> getPageOfPayoutAccounts(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE) Integer limit,
            @RequestParam(defaultValue = "createdAt") PayoutAccountSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayoutAccountDto> getPayoutAccount(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Long> getPayoutAccountBalance(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/balance-operations")
    public ResponseEntity<PageDto<BalanceOperationDto>> getPageOfBalanceOperations(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE) Integer limit,
            @RequestParam(defaultValue = "createdAt") BalanceOperationSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) OperationType operationType,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<PayoutAccountDto> createPayoutAccount(
            @RequestBody @Valid PayoutAccountAddingDto addingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/balance-operations/deposit")
    public ResponseEntity<BalanceOperationDto> deposit(
            @PathVariable UUID id,
            @RequestBody @Valid BalanceOperationAddingDto operationAddingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/balance-operations/withdraw")
    public ResponseEntity<BalanceOperationDto> withdraw(
            @PathVariable UUID id,
            @RequestBody @Valid BalanceOperationAddingDto operationAddingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/balance-operations/bonus")
    public ResponseEntity<BalanceOperationDto> sendBonus(
            @PathVariable UUID id,
            @RequestBody @Valid BalanceOperationAddingDto operationAddingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{id}/balance-operations/fine")
    public ResponseEntity<BalanceOperationDto> imposeFine(
            @PathVariable UUID id,
            @RequestBody @Valid BalanceOperationAddingDto operationAddingDto) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayoutAccountDto> updatePayoutAccount(
            @PathVariable UUID id,
            @RequestBody @NotEmpty
            @Size(max = 30, message = ValidationErrors.INVALID_STRING_MAX_LENGTH) String stripeAccountId) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activatePayoutAccount(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivatePayoutAccount(@PathVariable UUID id) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
