package com.cabaggregator.paymentservice.service;

import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.CustomerListPaymentMethodsParams;

import java.util.List;

public interface StripeService {
    Customer createCustomer(String email, String phone, String firstName, String lastName);

    Customer retrieveCustomer(String customerId);

    PaymentIntent createPaymentIntent(
            Customer customer, String description, Long unitAmount, String paymentMethodId);

    PaymentMethod getPaymentMethod(String paymentMethodId);

    PaymentMethod getCustomerDefaultPaymentMethod(Customer customer);

    List<PaymentMethod> getCustomerPaymentMethods(
            Customer customer, CustomerListPaymentMethodsParams.Type paymentMethodType);

    void setCustomerDefaultPaymentMethod(Customer customer, PaymentMethod paymentMethod);
}
