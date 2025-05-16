package com.example.rebookgateway.utils;

import com.example.rebookgateway.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String authInfo = exchange.getRequest().getHeaders().getFirst("Authorization");
        ServerHttpRequest mutatedRequest = exchange.getRequest();
        //auth
        if (path.equals("/api/auths/**")) {
            byte[] newBodyBytes = authService.getBytes(authInfo, path);
            mutatedRequest = authService.createAuthRequest(exchange, newBodyBytes);
        } else { // 일반
            String userId = jwtUtil.getUserId(authInfo);
            mutatedRequest.mutate().header("X-User-Id", userId)
                .build();
        }
        ServerWebExchange mutatedExchange = exchange.mutate()
            .request(mutatedRequest)
            .build();
        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
