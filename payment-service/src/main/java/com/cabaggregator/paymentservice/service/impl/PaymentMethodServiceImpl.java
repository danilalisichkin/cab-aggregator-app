package com.cabaggregator.paymentservice.service.impl;

import com.cabaggregator.paymentservice.core.constant.ApplicationMessages;
import com.cabaggregator.paymentservice.core.dto.payment.method.PaymentCardDto;
import com.cabaggregator.paymentservice.core.mapper.PaymentMapper;
import com.cabaggregator.paymentservice.entity.PaymentAccount;
import com.cabaggregator.paymentservice.exception.BadRequestException;
import com.cabaggregator.paymentservice.exception.ResourceNotFoundException;
import com.cabaggregator.paymentservice.service.PaymentMethodService;
import com.cabaggregator.paymentservice.service.StripeService;
import com.cabaggregator.paymentservice.stripe.enums.PaymentMethodType;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.param.CustomerListPaymentMethodsParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final StripeService stripeService;

    private final PaymentMapper paymentMapper;

    @Override
    public List<PaymentCardDto> getPaymentCards(PaymentAccount paymentAccount) {
        Customer stripeCustomer = getStripeCustomerFromPaymentAccount(paymentAccount);

        List<PaymentMethod> paymentCards =
                stripeService.getCustomerPaymentMethods(
                        stripeCustomer,
                        CustomerListPaymentMethodsParams.Type.CARD);

        return paymentMapper.methodListToCardDtoList(paymentCards);
    }

    @Override
    public PaymentCardDto getDefaultPaymentCard(PaymentAccount paymentAccount) {
        Customer stripeCustomer = getStripeCustomerFromPaymentAccount(paymentAccount);

        PaymentMethod paymentMethod = stripeService.getCustomerDefaultPaymentMethod(stripeCustomer);

        if (paymentMethod == null) {
            throw new ResourceNotFoundException(ApplicationMessages.DEFAULT_PAYMENT_METHOD_NOT_FOUND);
        }
        if (isNotCardPaymentMethod(paymentMethod)) {
            throw new ResourceNotFoundException(ApplicationMessages.DEFAULT_PAYMENT_METHOD_NOT_CARD);
        }

        return paymentMapper.methodToCardDto(paymentMethod);
    }

    @Override
    @Transactional
    public void setDefaultPaymentCard(PaymentAccount paymentAccount, String paymentMethodId) {
        Customer stripeCustomer = getStripeCustomerFromPaymentAccount(paymentAccount);

        PaymentMethod paymentMethod = stripeService.getPaymentMethod(paymentMethodId);

        if (isNotCardPaymentMethod(paymentMethod)) {
            throw new BadRequestException(ApplicationMessages.PROVIDED_PAYMENT_METHOD_NOT_CARD);
        }

        stripeService.setCustomerDefaultPaymentMethod(stripeCustomer, paymentMethod);
    }

    private Customer getStripeCustomerFromPaymentAccount(PaymentAccount paymentAccount) {
        return stripeService.retrieveCustomer(paymentAccount.getStripeCustomerId());
    }

    private boolean isNotCardPaymentMethod(PaymentMethod paymentMethod) {
        return !PaymentMethodType.CARD.getValue().equals(paymentMethod.getType());
    }
}
