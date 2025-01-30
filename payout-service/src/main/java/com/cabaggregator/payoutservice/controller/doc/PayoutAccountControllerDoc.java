package com.cabaggregator.payoutservice.controller.doc;

import com.cabaggregator.payoutservice.core.constant.ValidationErrors;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationAddingDto;
import com.cabaggregator.payoutservice.core.dto.BalanceOperationDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountAddingDto;
import com.cabaggregator.payoutservice.core.dto.PayoutAccountDto;
import com.cabaggregator.payoutservice.core.dto.page.PageDto;
import com.cabaggregator.payoutservice.core.enums.OperationType;
import com.cabaggregator.payoutservice.core.enums.sort.BalanceOperationSortField;
import com.cabaggregator.payoutservice.core.enums.sort.PayoutAccountSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

@SecurityRequirement(name = "token")
@Tag(name = "Payout Account API Controller",
        description = "Provides get, create, update operations above payment accounts and balance operations above them")
public interface PayoutAccountControllerDoc {

    @Operation(
            summary = "Get page of payout accounts",
            description = "Allows to get page of existing payout accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
    })
    ResponseEntity<PageDto<PayoutAccountDto>> getPageOfPayoutAccounts(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE) Integer limit,
            @RequestParam(defaultValue = "createdAt") PayoutAccountSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get payout account",
            description = "Allows to get existing payout account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist"),
    })
    ResponseEntity<PayoutAccountDto> getPayoutAccount(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id);

    @Operation(
            summary = "Get payout account balance",
            description = "Allows to get current balance of existing payout account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist"),
    })
    ResponseEntity<Long> getPayoutAccountBalance(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id);

    @Operation(
            summary = "Get page of balance operations",
            description = "Allows to get page of balance operations above existing account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist"),
    })
    ResponseEntity<PageDto<BalanceOperationDto>> getPageOfBalanceOperations(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE) Integer limit,
            @RequestParam(defaultValue = "createdAt") BalanceOperationSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) OperationType operationType,
            @RequestParam(required = false) LocalDateTime startTime,
            @RequestParam(required = false) LocalDateTime endTime);

    @Operation(
            summary = "Create payout account",
            description = "Allows to create new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409", description = "Conflict: payout account with provided data already exists")
    })
    ResponseEntity<PayoutAccountDto> createPayoutAccount(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new payout account")
            @RequestBody @Valid PayoutAccountAddingDto addingDto);

    @Operation(
            summary = "Deposit to account",
            description = "Allows to deposit money to account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist")
    })
    ResponseEntity<BalanceOperationDto> deposit(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id,
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new balance operation")
            @RequestBody @Valid BalanceOperationAddingDto operationAddingDto);

    @Operation(
            summary = "Withdraw from account",
            description = "Allows to withdraw money from account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist")
    })
    ResponseEntity<BalanceOperationDto> withdraw(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id,
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new balance operation")
            @RequestBody @Valid BalanceOperationAddingDto operationAddingDto);

    @Operation(
            summary = "Send bonus to account",
            description = "Allows to send bonus to account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist")
    })
    ResponseEntity<BalanceOperationDto> sendBonus(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id,
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new balance operation")
            @RequestBody @Valid BalanceOperationAddingDto operationAddingDto);

    @Operation(
            summary = "Impose fine on account",
            description = "Allows to impose fine on account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist")
    })
    ResponseEntity<BalanceOperationDto> imposeFine(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id,
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new balance operation")
            @RequestBody @Valid BalanceOperationAddingDto operationAddingDto);

    @Operation(
            summary = "Update payout account",
            description = "Allows to update existing payout account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist"),
            @ApiResponse(responseCode = "409",
                    description = "Conflict: payout account with provided stripe account already exists")
    })
    ResponseEntity<PayoutAccountDto> updatePayoutAccount(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id,
            @Parameter(
                    name = "Stripe account id",
                    description = "Id of existing stripe account that will be connected with payout account")
            @RequestBody @NotEmpty
            @Size(max = 30, message = ValidationErrors.INVALID_STRING_MAX_LENGTH) String stripeAccountId);

    @Operation(
            summary = "Activate payout account",
            description = "Allows to activate existing payout account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist"),
    })
    ResponseEntity<Void> activatePayoutAccount(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id);

    @Operation(
            summary = "Deactivate payout account",
            description = "Allows to deactivate existing payout account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payout account does not exist"),
    })
    ResponseEntity<Void> deactivatePayoutAccount(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id);
}
