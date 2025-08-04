package com.example.rebookgateway;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    private static final String[] WHITE_LIST = {
        "/actuator/**",
        "/eureka/**",
        "/swagger-ui.html",
        "/favicon.ico ",
        "/webjars/**",
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v3/api-docs/**",
        "/api/auths/**",
        "/api/users/**",
        "/api/books/**",
        "/api/tradings/**",
        "/api/chats/**",
        "/api/notifications/**",
        "/api/ws-chat/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
//            .authorizeExchange(exchange -> exchange
//                .anyExchange().permitAll()
//            )
            .authorizeExchange(exchange -> exchange
                .pathMatchers(WHITE_LIST).permitAll() // 배열을 그대로 사용
                .anyExchange().authenticated()
            )
            .build();
    }

}

