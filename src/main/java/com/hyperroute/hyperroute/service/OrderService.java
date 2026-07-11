package com.hyperroute.hyperroute.service;

import com.hyperroute.hyperroute.dto.OrderCreatedEvent;
import com.hyperroute.hyperroute.dto.OrderRequest;
import com.hyperroute.hyperroute.model.Order;
import com.hyperroute.hyperroute.model.OrderStatus;
import com.hyperroute.hyperroute.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "order-events";

    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Order placeOrder(OrderRequest request) {

        Order order = new Order();
        order.setCustomerId(request.customerId());
        order.setLatitude(request.latitude());
        order.setLongitude(request.longitude());
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getLatitude(),
                savedOrder.getLongitude(),
                savedOrder.getCreatedAt()
        );

        // Stream non-blocking event out to Kafka cluster
        kafkaTemplate.send(TOPIC, savedOrder.getId().toString(), event);
        System.out.printf("[Order Service] Event emitted for Order ID: %d%n", savedOrder.getId());

        return savedOrder;
    }
}
