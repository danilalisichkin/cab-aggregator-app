package com.cabaggregator.paymentservice.controller.doc;

import com.cabaggregator.paymentservice.core.constant.ValidationErrors;
import com.cabaggregator.paymentservice.core.dto.page.PageDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountAddingDto;
import com.cabaggregator.paymentservice.core.dto.payment.account.PaymentAccountDto;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.enums.sort.PaymentAccountSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@SecurityRequirement(name = "token")
@Tag(name = "Payment Account API Controller",
        description = "Provides get, create, update operations above payment accounts")
public interface PaymentAccountControllerDoc {

    @Operation(
            summary = "Get page of payment accounts",
            description = "Allows to get page of existing payment accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields")
    })
    ResponseEntity<PageDto<PaymentAccountDto>> getPageOfPaymentAccounts(
            @RequestParam(defaultValue = "0")
            @PositiveOrZero(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE_OR_ZERO) Integer offset,
            @RequestParam(defaultValue = "10")
            @Positive(message = ValidationErrors.NUMBER_IS_NOT_POSITIVE) Integer limit,
            @RequestParam(defaultValue = "createdAt") PaymentAccountSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder);

    @Operation(
            summary = "Get payment account",
            description = "Allows to get existing payment account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payment account does not exist"),
    })
    ResponseEntity<PaymentAccountDto> getPaymentAccount(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id);

    @Operation(
            summary = "Get payment cards",
            description = "Allows to get payment cards of related payment account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404", description = "Not found: payment account does not exist"),
    })
    ResponseEntity<List<PaymentCardDto>> getPaymentCards(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id);

    @Operation(
            summary = "Get default payment card",
            description = "Allows to get default payment card of related payment account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404",
                    description = "Not found: payment account does not exist; default payment method not set or is not a card"),
    })
    ResponseEntity<PaymentCardDto> getDefaultPaymentCard(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id);

    @Operation(
            summary = "Create payment account",
            description = "Allows to create new payment account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "409", description = "Conflict: payment account with provided data already exists")
    })
    ResponseEntity<PaymentAccountDto> createPaymentAccount(
            @Parameter(
                    name = "Required data",
                    description = "Data that is required to create new payment account")
            @RequestBody @Valid PaymentAccountAddingDto addingDto);

    @Operation(
            summary = "Set default payment card",
            description = "Allows to connect payment card to related payment account as default payment method")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful response"),
            @ApiResponse(responseCode = "400",
                    description = "Bad request: invalid parameters or missing required fields; payment method is not card"),
            @ApiResponse(responseCode = "404",
                    description = "Not found: payment account does not exist; payment method not found"),
    })
    ResponseEntity<Void> setDefaultPaymentCard(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id,
            @Parameter(
                    name = "Stripe payment method id",
                    description = "Id of existing stripe payment method that will be set as default")
            @RequestBody @Size(min = 1, max = 40, message = ValidationErrors.INVALID_STRING_LENGTH)
            String paymentMethodId);

    @Operation(
            summary = "Update payment account",
            description = "Allows to update existing payment account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400",
                    description = "Bad request: invalid parameters or missing required fields"),
            @ApiResponse(responseCode = "404",
                    description = "Not found: payment account does not exist; stripe account does not exist"),
            @ApiResponse(responseCode = "409", description = "Conflict: payment account with provided stripe account exists")
    })
    ResponseEntity<PaymentAccountDto> updatePaymentAccount(
            @Parameter(
                    description = "Account identifier")
            @PathVariable UUID id,
            @Parameter(
                    name = "Stripe account id",
                    description = "Id of existing stripe customer account that will be connected with payment account")
            @RequestBody @Size(min = 1, max = 30, message = ValidationErrors.INVALID_STRING_LENGTH)
            String stripeAccountId);
}
