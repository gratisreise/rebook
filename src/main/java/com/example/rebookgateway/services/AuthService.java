package com.example.rebookgateway.services;

import com.example.rebookgateway.exceptions.CInvalidDataException;
import com.example.rebookgateway.exceptions.CUnAuthorizedException;
import com.example.rebookgateway.model.RefreshRequest;
import com.example.rebookgateway.model.UserInfo;
import com.example.rebookgateway.utils.JwtUtil;
import com.example.rebookgateway.utils.KeycloakService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KeycloakService keycloakService;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;
    private final String refreshPrefix = "refresh:";

    //json 바이트 배열 생성
    public byte[] getBytes(String info, String path) {
        Object object = path.contains("login") ? getUserInfo(info) : getRefreshRequest(info);
        String jsonString = objectToJsonString(object);
        return jsonString.getBytes(StandardCharsets.UTF_8);
    }

    //요청수정
    public ServerHttpRequestDecorator createAuthRequest(ServerWebExchange exchange,
        byte[] newBodyBytes) {
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public Flux<DataBuffer> getBody() {
                return Flux.just(bufferFactory.wrap(newBodyBytes));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.putAll(super.getHeaders());
                headers.setContentLength(newBodyBytes.length);
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

    //유저정보 생성
    private UserInfo getUserInfo(String code) {
        UserInfo userInfo = keycloakService.getUserInfo(code);
        String userId = userInfo.getUserId();

        //토큰주입
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);
        userInfo.setAccessToken(accessToken);
        userInfo.setRefreshToken(refreshToken);

        //캐싱
        redisService.set(refreshPrefix + refreshToken, "true");
        return userInfo;
    }

    //리프레쉬
    private RefreshRequest getRefreshRequest(String refreshToken) {
        //캐싱확인
        redisService.get(refreshPrefix + refreshToken)
            .orElseThrow(CUnAuthorizedException::new);
        //토큰발급
        String userId = jwtUtil.getUserId(refreshToken);
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshedToken = jwtUtil.createRefreshToken(userId);

        //캐싱
        redisService.set(refreshPrefix + refreshToken, "true");

        return new RefreshRequest(accessToken, refreshedToken);
    }



    private String objectToJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CInvalidDataException(e.getMessage());
        }
    }


}
