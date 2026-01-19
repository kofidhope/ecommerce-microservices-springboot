package com.kofi.ecommerce.order;

import com.kofi.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(

        Integer id,

        String reference,

        @Positive(message = "Enter positive amount")
        BigDecimal amount,

        @NotNull(message = "Payment method should not be null")
        PaymentMethod paymentMethod,

        @NotNull(message = "customer should be present")
        @NotEmpty(message = "customer should be present")
        @NotBlank(message = "customer should be present")
        String customerId,

        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products
) {
}
