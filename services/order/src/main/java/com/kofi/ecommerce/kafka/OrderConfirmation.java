package com.kofi.ecommerce.kafka;

import com.kofi.ecommerce.customer.CustomerResponse;
import com.kofi.ecommerce.order.PaymentMethod;
import com.kofi.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> product
){
}
