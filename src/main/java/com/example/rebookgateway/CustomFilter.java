package com.example.rebookgateway;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String token = getToken(exchange);
        if(token.isBlank() || !jwtUtil.validateToken(token)) {
            return onError(exchange, "Token Not Found", HttpStatus.UNAUTHORIZED);
        }

        String userId = jwtUtil.getUserId(token);

        ServerHttpRequest mutatedRequest = exchange.getRequest()
            .mutate().header("X-User-Id", userId)
            .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
            .request(mutatedRequest)
            .build();
        return chain.filter(mutatedExchange);
    }

    private String getToken(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders()
            .getFirst("Authorization").substring(7);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String e, HttpStatus httpStatus) {
        ServerHttpResponse httpResponse = exchange.getResponse();
        httpResponse.setStatusCode(httpStatus);

        return httpResponse.setComplete();
    }


    @Override
    public int getOrder() {
        return -10;
    }
}
