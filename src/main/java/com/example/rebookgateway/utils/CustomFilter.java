package com.example.rebookgateway.utils;

import com.example.rebookgateway.exceptions.CUnAuthorizedException;
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
        String info = path.equals("/api/auths/login") ? getToken(exchange) : getCode(exchange);
        ServerHttpRequest mutatedRequest = exchange.getRequest();

        if(info.isEmpty()){
            throw new CUnAuthorizedException("토큰이 비어있습니다.");
        }

        //auth
        if (path.equals("/api/auths/login") || path.equals("/api/auths/refresh")) {
            byte[] newBodyBytes = authService.getBytes(info, path);
            mutatedRequest = authService.createAuthRequest(exchange, newBodyBytes);
        } else { // 일반
            if(jwtUtil.validateToken(info)){
                throw new CUnAuthorizedException("유효하지 않은 리프레쉬 토큰");
            }
            String userId = jwtUtil.getUserId(info);
            mutatedRequest.mutate().header("X-User-Id", userId)
                .build();
        }
        ServerWebExchange mutatedExchange = exchange.mutate()
            .request(mutatedRequest)
            .build();
        return chain.filter(mutatedExchange);
    }

    private static String getCode(ServerWebExchange exchange) {
        return exchange.getRequest().getQueryParams().getFirst("code");
    }

    private static String getToken(ServerWebExchange exchange) {
        return exchange.getRequest().getHeaders()
            .getFirst("Authorization").substring(7);
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
