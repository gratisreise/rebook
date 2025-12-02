package com.example.rebooknotificationservice.config;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // 1. 큐 이름
    private static final String BOOK_NOTIFICATION_QUEUE = "book.notification.queue";
    private static final String TRADE_NOTIFICATION_QUEUE = "trade.notification.queue";
    private static final String CHAT_NOTIFICATION_QUEUE = "chat.notification.queue";

    // 2. 익스체인지 이름 (각각 분리)
    private static final String BOOK_NOTIFICATION_EXCHANGE = "book.notification.exchange";
    private static final String TRADE_NOTIFICATION_EXCHANGE = "trade.notification.exchange";
    private static final String CHAT_NOTIFICATION_EXCHANGE = "chat.notification.exchange";

    // 3. 라우팅키 (각각 분리)
    private static final String BOOK_ROUTING_KEY = "book.notification.key";
    private static final String TRADE_ROUTING_KEY = "trade.notification.key";
    private static final String CHAT_ROUTING_KEY = "chat.notification.key";

    // --- BOOK ---
    @Bean
    public Queue bookNotificationQueue() {
        return new Queue(BOOK_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public TopicExchange bookNotificationExchange() {
        return new TopicExchange(BOOK_NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding bookNotificationBinding() {
        return BindingBuilder.bind(bookNotificationQueue())
            .to(bookNotificationExchange())
            .with(BOOK_ROUTING_KEY);
    }

    // --- TRADE ---
    @Bean
    public Queue tradeNotificationQueue() {
        return new Queue(TRADE_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public TopicExchange tradeNotificationExchange() {
        return new TopicExchange(TRADE_NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding tradeNotificationBinding() {
        return BindingBuilder.bind(tradeNotificationQueue())
            .to(tradeNotificationExchange())
            .with(TRADE_ROUTING_KEY);
    }

    // --- CHAT ---
    @Bean
    public Queue chatNotificationQueue() {
        return new Queue(CHAT_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public TopicExchange chatNotificationExchange() {
        return new TopicExchange(CHAT_NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding chatNotificationBinding() {
        return BindingBuilder.bind(chatNotificationQueue())
            .to(chatNotificationExchange())
            .with(CHAT_ROUTING_KEY);
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory,
        MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate; // AmqpTemplate 타입으로 노출
    }
}
