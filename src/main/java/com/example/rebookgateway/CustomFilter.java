package com.example.rebookgateway;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


//일반 요청 => 패스포트 필요
//인즈&인가 요청 => 그냥 바로 서비스로 이어줌
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomFilter implements GlobalFilter, Ordered {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String PASSPORT_HEADER = "X-Passport";
    private final JwtUtil jwtUtil;
    private final WebClient.Builder lbWebClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String uri = exchange.getRequest().getPath().toString();
        log.info("uri: {}", uri);

        // 인증 & 인가 요청
        if (uri.startsWith("/api/auth")) {
            return chain.filter(exchange);
        }
        // swagger api 문서 요청
        if (uri.startsWith("/swagger-ui") || uri.startsWith("/v3/api-docs") || uri.startsWith(
            "/swagger-resources")) {
            return chain.filter(exchange);
        }

        // 웹소켓 요청
        if (uri.startsWith("/api/ws-chat")) {
            return webSocketConnect(exchange, chain);
        }

        //토큰추출
        String token = getToken(exchange);

        if (token.isBlank()) { // 없으면 SSE연결이라고?? => 개선해야할 듯 연결조건자체를 변경해봐야할 듯
            Map<String, String> params = exchange.getRequest().getQueryParams().toSingleValueMap();
            token = params.get("token");
            if (token == null || token.isBlank() || !jwtUtil.validateToken(token)) {
                return onError(exchange);
            }
            log.info("sse토큰:{}", token);
        }
        log.info("token: {}", token);

        //토큰검증
        if (token == null || token.isBlank() || !jwtUtil.validateToken(token)) {
            log.error("토큰이 없거나 유효하지 않음");
            return onError(exchange);
        }

        return getPassport(exchange, chain, token);
    }

    private Mono<Void> getPassport(ServerWebExchange exchange, GatewayFilterChain chain,
        String token) {
        return lbWebClient
            .build()
            .post()
            .uri(uriBuilder -> uriBuilder
                .scheme("lb")
                .host("AUTH-SERVICE")
                .path("/passports")
                .queryParam("jwt", token)
                .build()
            )
            .retrieve()
            .bodyToMono(String.class)
            .flatMap(passport -> {
                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header(PASSPORT_HEADER, passport)
                    .build();
                return chain.filter(exchange.mutate().request(mutatedRequest).build());
            });
    }

    private Mono<Void> webSocketConnect(ServerWebExchange exchange, GatewayFilterChain chain) {
        Map<String, String> params = exchange.getRequest().getQueryParams().toSingleValueMap();
        String token = params.get("token");
        log.info("웹소켓연결");

        if (token != null && !jwtUtil.validateToken(token)) {
            log.error("토큰이 없거나 유효하지 않음");
            return onError(exchange);
        }

        return chain.filter(exchange);
    }

    private String getToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return "";
        }
        return authHeader.substring(BEARER_PREFIX.length());
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
