package com.kofi.ecommerce.orderline;

import com.kofi.ecommerce.order.Order;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class OrderLine {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinTable(name = "order_id")
    private Order order;

    private Integer productId;

    private double quantity;
}
