package com.kofi.ecommerce.payment;

import com.kofi.ecommerce.customer.CustomerResponse;
import com.kofi.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
