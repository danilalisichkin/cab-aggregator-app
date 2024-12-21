package com.cabaggregator.paymentservice.service;

import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.entity.PaymentAccount;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentCardDto> getPaymentCards(PaymentAccount paymentAccount);

    PaymentCardDto getDefaultPaymentCard(PaymentAccount paymentAccount);

    void setDefaultPaymentCard(PaymentAccount paymentAccount, String paymentMethodId);
}
