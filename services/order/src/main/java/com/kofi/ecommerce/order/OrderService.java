package com.kofi.ecommerce.order;

import com.kofi.ecommerce.customer.CustomerClient;
import com.kofi.ecommerce.exception.BusinessException;
import com.kofi.ecommerce.kafka.OrderConfirmation;
import com.kofi.ecommerce.kafka.OrderProducer;
import com.kofi.ecommerce.orderline.OrderLineRequest;
import com.kofi.ecommerce.orderline.OrderLineService;
import com.kofi.ecommerce.payment.PaymentClient;
import com.kofi.ecommerce.payment.PaymentRequest;
import com.kofi.ecommerce.product.PurchaseRequest;
import com.kofi.ecommerce.product.productClient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final productClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest request) {
        // check if customer exist(using feingClient)
            var customer = this.customerClient.findCustomerById(request.customerId())
                    .orElseThrow(() -> new BusinessException("cannot create order:: No customer exist with this id"));

        //purchase the product (using product micro-service using restTemplate)
            var purchasedProducts = this.productClient.purchaseProducts(request.products());
        //persist order
            var order = this.orderRepository.save(mapper.toOrder(request));
        //persist orderLines
            for (PurchaseRequest purchaseRequest: request.products()){
                orderLineService.saveOrderLine(
                        new OrderLineRequest(
                                null,
                                order.getId(),
                                purchaseRequest.productId(),
                                purchaseRequest.quantity()
                        )
                );
            }
        //todo start payment process
            var paymentRequest = new PaymentRequest(
                    request.amount(),
                    request.paymentMethod(),
                    order.getId(),
                    order.getReference(),
                    customer
            );
            paymentClient.requestOrderPayment(paymentRequest);
        //send the order confirmation to notification service
            orderProducer.sendOrderConfirmation(
                    new OrderConfirmation(
                            request.reference(),
                            request.amount(),
                            request.paymentMethod(),
                            customer,
                            purchasedProducts
                    )
            );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(()->new EntityNotFoundException(String.format("No order found with this ID: %d", orderId)));
    }
}
