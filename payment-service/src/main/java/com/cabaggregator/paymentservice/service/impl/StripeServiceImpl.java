package com.cabaggregator.paymentservice.service.impl;

import com.cabaggregator.paymentservice.core.constant.StringTemplates;
import com.cabaggregator.paymentservice.service.StripeService;
import com.cabaggregator.paymentservice.stripe.enums.Currency;
import com.cabaggregator.paymentservice.stripe.exception.StripeExceptionHandler;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.PaymentMethodCollection;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListPaymentMethodsParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

    private final StripeExceptionHandler stripeExceptionHandler;

    @Override
    public Customer createCustomer(String email, String phone, String firstName, String lastName) {
        CustomerCreateParams requestParams = CustomerCreateParams.builder()
                .setEmail(email)
                .setPhone(phone)
                .setName(String.format(StringTemplates.FULL_NAME, firstName, lastName))
                .build();

        Customer customer = new Customer();
        try {
            customer = Customer.create(requestParams);
        } catch (StripeException e) {
            stripeExceptionHandler.handle(e);
        }

        return customer;
    }

    @Override
    public Customer retrieveCustomer(String customerId) {
        Customer customer = new Customer();
        try {
            customer = Customer.retrieve(customerId);
        } catch (StripeException e) {
            stripeExceptionHandler.handle(e);
        }

        return customer;
    }

    @Override
    public PaymentIntent createPaymentIntent(
            Customer customer, String description, Long unitAmount, String paymentMethodId) {

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(unitAmount)
                .setCurrency(Currency.USD.getValue())
                .setCustomer(customer.getId())
                .setDescription(description)
                .setPaymentMethod(paymentMethodId)
                .setReceiptEmail(customer.getEmail())
                .build();

        PaymentIntent paymentIntent = new PaymentIntent();
        try {
            paymentIntent = PaymentIntent.create(params);
        } catch (StripeException e) {
            stripeExceptionHandler.handle(e);
        }

        return paymentIntent;
    }

    @Override
    public void setCustomerDefaultPaymentMethod(Customer customer, PaymentMethod paymentMethod) {
        try {
            CustomerUpdateParams params = CustomerUpdateParams.builder()
                    .setInvoiceSettings(
                            CustomerUpdateParams.InvoiceSettings.builder()
                                    .setDefaultPaymentMethod(paymentMethod.getId())
                                    .build())
                    .build();

            customer.update(params);
        } catch (StripeException e) {
            stripeExceptionHandler.handle(e);
        }
    }

    @Override
    public PaymentMethod getCustomerDefaultPaymentMethod(Customer customer) {
        return customer
                .getInvoiceSettings()
                .getDefaultPaymentMethodObject();
    }

    @Override
    public List<PaymentMethod> getCustomerPaymentMethods(
            Customer customer, CustomerListPaymentMethodsParams.Type paymentMethodType) {

        CustomerListPaymentMethodsParams params = CustomerListPaymentMethodsParams.builder()
                .setType(paymentMethodType)
                .build();

        PaymentMethodCollection attachedMethods = new PaymentMethodCollection();
        try {
            attachedMethods = customer.listPaymentMethods(params);
        } catch (StripeException e) {
            stripeExceptionHandler.handle(e);
        }

        return attachedMethods.getData();
    }

    @Override
    public PaymentMethod getPaymentMethod(String paymentMethodId) {
        PaymentMethod paymentMethod = new PaymentMethod();
        try {
            paymentMethod = PaymentMethod.retrieve(paymentMethodId);
        } catch (StripeException e) {
            stripeExceptionHandler.handle(e);
        }

        return paymentMethod;
    }
}
