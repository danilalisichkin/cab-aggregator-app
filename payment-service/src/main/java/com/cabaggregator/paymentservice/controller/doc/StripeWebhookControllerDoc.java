package com.cabaggregator.paymentservice.controller.doc;

import com.cabaggregator.paymentservice.core.constant.ValidationErrors;
import com.cabaggregator.paymentservice.stripe.StripeHttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Stripe Webhook Controller",
        description = "Catches events from stripe related to payments")
public interface StripeWebhookControllerDoc {

    @Operation(
            summary = "Handle webhook event",
            description = "Allows to handle stripe webhook events related to payments")
    ResponseEntity<Void> handleStripeWebhook(
            @RequestBody @NotEmpty(message = ValidationErrors.STRING_IS_EMPTY) String payload,
            @RequestHeader(StripeHttpHeaders.STRIPE_SIGNATURE) String sigHeader);
}
