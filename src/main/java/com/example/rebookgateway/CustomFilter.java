package com.example.rebookgateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CustomFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getURI().toString();
        log.info("uri: {}", uri);
        if (uri.contains("/api/auths")) {
            return chain.filter(exchange);
        }
        String token = getToken(exchange);
        log.info("token: {}", token);
        if (token.isBlank() || !jwtUtil.validateToken(token)) {
            log.error("토큰이 없거나 유효하지 않음");
            return onError(exchange, "Token Not Found", HttpStatus.UNAUTHORIZED);
        }

        String userId = jwtUtil.getUserId(token);
        log.info("User Id: {}", userId);

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
            .header("X-User-Id", userId).build();

        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
        log.info("Mutated Exchange: {}", mutatedExchange);
        return chain.filter(mutatedExchange);
    }

    private String getToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        log.info("authHeader: {}", authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "";
        }
        int prefixLength = "Bearer ".length();
        return authHeader.substring(prefixLength);
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
