package com.cabaggregator.paymentservice.service.impl;

import com.cabaggregator.paymentservice.entity.enums.PaymentStatus;
import com.cabaggregator.paymentservice.exception.InternalErrorException;
import com.cabaggregator.paymentservice.service.PaymentService;
import com.cabaggregator.paymentservice.service.StripeWebhookService;
import com.cabaggregator.paymentservice.stripe.config.StripeConfig;
import com.cabaggregator.paymentservice.stripe.enums.EventTypePrefix;
import com.cabaggregator.paymentservice.stripe.enums.StripePaymentStatus;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeWebhookServiceImpl implements StripeWebhookService {

    private final StripeConfig stripeConfig;

    private final PaymentService paymentService;

    @Override
    public void processWebhookEvent(String payload, String sigHeader) {
        Event event = constructEvent(payload, sigHeader);

        String[] eventTypeParts = event.getType().split("\\.");
        if (eventTypeParts.length != 2) {
            throw new InternalErrorException("Got event with invalid format: " + event.getType());
        }
        String prefix = eventTypeParts[0];
        String suffix = eventTypeParts[1];

        if (EventTypePrefix.PAYMENT_INTENT.getValue().equals(prefix)) {
            processPaymentIntentEvent(suffix, event);
        } else {
            throw new InternalErrorException("Unhandled event type prefix: " + prefix);
        }
    }

    private Event constructEvent(String payload, String sigHeader) {
        try {
            return Webhook.constructEvent(payload, sigHeader, stripeConfig.getEndpointSecret());
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    private void processPaymentIntentEvent(String suffix, Event event) {
        String paymentIntentId = extractPaymentIntentId(event);
        StripePaymentStatus suffixAsStripePaymentStatus = StripePaymentStatus.valueOf(suffix);
        PaymentStatus paymentStatus = suffixAsStripePaymentStatus.getInternalStatus();

        paymentService.updatePaymentStatus(paymentIntentId, paymentStatus);
    }

    private String extractPaymentIntentId(Event event) {
        return ((PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new InternalErrorException("Unable to deserialize PaymentIntent")))
                .getId();
    }
}
