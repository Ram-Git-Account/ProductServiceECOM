package com.productservice.productservice.controllers;

import com.productservice.productservice.dtos.CreateOrderDto;
import com.productservice.productservice.models.Order;
import com.productservice.productservice.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    public  OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderDto dto) {
        return orderService.createOrder(dto.getUserId(), dto.getProductIds());
    }

    @PostMapping("/{orderId}/pay")
    public String initiatePayment(@PathVariable UUID orderId) {
        return orderService.initiatePayment(orderId);
    }

    @PostMapping("/{orderId}/paid")
    public void markPaid(@PathVariable UUID orderId,
                         @RequestParam String paymentRef) {
        orderService.markOrderPaid(orderId, paymentRef);
    }
}
