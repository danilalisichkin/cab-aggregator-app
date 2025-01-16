package com.cabaggregator.paymentservice.controller.api;

import com.cabaggregator.paymentservice.controller.doc.StripeWebhookControllerDoc;
import com.cabaggregator.paymentservice.core.constant.ValidationErrors;
import com.cabaggregator.paymentservice.service.StripeWebhookService;
import com.cabaggregator.paymentservice.stripe.StripeHttpHeaders;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/stripe/webhooks")
public class StripeWebhookController implements StripeWebhookControllerDoc {

    private final StripeWebhookService stripeWebhookService;

    @PostMapping
    public ResponseEntity<Void> handleStripeWebhook(
            @RequestBody @NotEmpty(message = ValidationErrors.STRING_IS_EMPTY) String payload,
            @RequestHeader(StripeHttpHeaders.STRIPE_SIGNATURE) String sigHeader) {

        stripeWebhookService.processWebhookEvent(payload, sigHeader);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
