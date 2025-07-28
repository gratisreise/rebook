package com.example.rebookchatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final String[] WHITE_LIST ={"http://localhost:5173", "https://rebookk.click"};

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 엔드포인트 등록
        registry.addEndpoint("/api/ws-chat")
            .setAllowedOrigins(WHITE_LIST)
            .withSockJS()
            .setSuppressCors(true);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 구독 주소(prefix)와 메시지 발행 주소(prefix) 설정
        registry.enableSimpleBroker("/topic"); // 구독용
        registry.setApplicationDestinationPrefixes("/app"); // 발행용
    }

}
