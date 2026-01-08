package com.productservice.productservice.services;

import com.productservice.productservice.dtos.InitiatePaymentRequestDto;
import com.productservice.productservice.models.Order;
import com.productservice.productservice.models.OrderStatus;
import com.productservice.productservice.models.Product;
import com.productservice.productservice.repositories.OrderRepository;
import com.productservice.productservice.repositories.ProductRepository;
import org.springframework.http.HttpEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    public Order createOrder(Long userId, List<UUID> productIds) {

        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            throw new RuntimeException("One or more products not found");
        }

        long totalAmount = 0;
        for (Product product : products) {
            if (product.getPrice() == null) {
                throw new RuntimeException("Product price missing for product " + product.getId());
            }
            totalAmount += product.getPrice().getValue();
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setProducts(products);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.CREATED);

        return orderRepository.save(order);
    }


    public String initiatePayment(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Invalid order state");
        }

        InitiatePaymentRequestDto dto = new InitiatePaymentRequestDto();
        dto.setOrderId(orderId.toString());
        dto.setAmount(order.getTotalAmount());
        dto.setPhoneNumber("9999999999");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InitiatePaymentRequestDto> entity =
                new HttpEntity<>(dto, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "http://localhost:7070/payments/",
                        entity,
                        String.class
                );

        order.setStatus(OrderStatus.PAYMENT_INITIATED);
        orderRepository.save(order);

        return response.getBody(); // payment link
    }

    public void markOrderPaid(UUID orderId, String paymentRef) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.PAID);
        order.setPaymentReference(paymentRef);
        orderRepository.save(order);
    }
}

