package com.example.rebookchatservice.utils;

import com.example.rebookchatservice.model.message.NotificationChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationPublisher {

    private final AmqpTemplate amqpTemplate;

    @Value("${notification.exchange}")
    private String exchange;

    @Value("${notification.routing-key}")
    private String routingKey;


    public void sendNotification(NotificationChatMessage message) {
        log.info("notificationMessgae {}", message.toString());
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }
}