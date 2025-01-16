package com.cabaggregator.paymentservice.controller.doc;

import com.cabaggregator.paymentservice.core.dto.payment.PaymentDto;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentRequest;
import com.cabaggregator.paymentservice.core.dto.payment.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Payment API Controller",
        description = "Provides get, create operations above payments")
public interface PaymentControllerDoc {

    @Operation(
            summary = "Create payment",
            description = "Allows to create new payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields"),
    })
    ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest paymentRequest);

    @Operation(
            summary = "Get list of ride payments",
            description = "Allows to get whole history of payment attempts for specified ride")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response"),
            @ApiResponse(responseCode = "400", description = "Bad request: invalid parameters or missing required fields")
    })
    ResponseEntity<List<PaymentDto>> getRidePayments(@PathVariable ObjectId rideId);
}
