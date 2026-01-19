package com.kofi.ecommerce.orderline;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        double quantity
) {

}
