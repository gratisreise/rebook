package com.example.rebookgateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient authWebClient() {
        return WebClient.builder()
            .baseUrl("lb://AUTH-SERVICE")   // ✔ Eureka 기반 LoadBalancer 적용
            .build();
    }
}