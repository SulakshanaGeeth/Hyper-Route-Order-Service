package com.hyperroute.hyperroute.consumer;

import com.hyperroute.hyperroute.dto.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RiderNotificationConsumer {

    /**
     * Listens asynchronously to the order event stream.
     * Separating this prevents the HTTP Controller from waiting on notification tasks.
     */
    @KafkaListener(topics = "order-events", groupId = "rider-notification-group")
    public void handleOrderCreatedNotification(OrderCreatedEvent event) {
        System.out.printf("[Notification System] Fetching active riders near location: [%.4f, %.4f]%n",
                event.latitude(), event.longitude());

        System.out.printf("[Dispatcher Alert] Order #%d is available! Alerting closest delivery agents via WebSockets...%n",
                event.orderId());

        // Next phase connection: Inject your WebSocket Session manager or
        // Redis Geo-Queries here to selectively ping the nearest active rider apps.
    }
}