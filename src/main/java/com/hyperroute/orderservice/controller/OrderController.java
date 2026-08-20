package com.hyperroute.orderservice.controller;

import com.hyperroute.orderservice.dto.OrderRequest;
import com.hyperroute.orderservice.model.Order;
import com.hyperroute.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        Order savedOrder = orderService.placeOrder(request);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
}
