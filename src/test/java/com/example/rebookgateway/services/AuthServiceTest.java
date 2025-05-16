package com.example.rebookgateway.services;

import com.example.rebookgateway.exceptions.CInvalidDataException;
import com.example.rebookgateway.exceptions.CUnAuthorizedException;
import com.example.rebookgateway.model.RefreshRequest;
import com.example.rebookgateway.model.UserInfo;
import com.example.rebookgateway.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private KeycloakService keycloakService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisService redisService;

    @Mock
    private ServerWebExchange exchange;

    @Mock
    private ServerHttpRequest request;

    @Mock
    private DataBufferFactory dataBufferFactory;

    @Mock
    private DataBuffer dataBuffer;

    @Mock
    private HttpHeaders headers;

    @InjectMocks
    private AuthService authService;

    private UserInfo userInfo;
    private RefreshRequest refreshRequest;
    private final String code = "test-code";
    private final String refreshToken = "test-refresh-token";
    private final String userId = "test-user-id";
    private final String accessToken = "access-token";
    private final String newRefreshToken = "new-refresh-token";

    @BeforeEach
    void setUp() {
        userInfo = UserInfo.builder()
            .userId(userId)
            .username("test-user")
            .email("test@example.com")
            .role("USER")
            .build();
    }

    @Test
    void getBytes_loginPath_success() {
        // Given
        String path = "/api/auths/login";
        when(keycloakService.getUserInfo(code)).thenReturn(userInfo);
        when(jwtUtil.createAccessToken(userId)).thenReturn(accessToken);
        when(jwtUtil.createRefreshToken(userId)).thenReturn(refreshToken);
        doNothing().when(redisService).set(anyString(), anyString());

        // When
        byte[] result = authService.getBytes(code, path);

        // Then
        String jsonResult = new String(result, StandardCharsets.UTF_8);
        assertNotNull(result);
        assertTrue(jsonResult.contains(userId));
        assertTrue(jsonResult.contains("test-user"));
        assertTrue(jsonResult.contains(accessToken));
        assertTrue(jsonResult.contains(refreshToken));
        verify(redisService).set(eq("refresh:" + refreshToken), eq("true"));
    }

    @Test
    void getBytes_refreshPath_success() {
        // Given
        String path = "/api/auths/refresh";
        when(redisService.get("refresh:" + refreshToken)).thenReturn(Optional.of("true"));
        when(jwtUtil.getUserId(refreshToken)).thenReturn(userId);
        when(jwtUtil.createAccessToken(userId)).thenReturn(accessToken);
        when(jwtUtil.createRefreshToken(userId)).thenReturn(newRefreshToken);
        doNothing().when(redisService).set(anyString(), anyString());

        // When
        byte[] result = authService.getBytes(refreshToken, path);

        // Then
        String jsonResult = new String(result, StandardCharsets.UTF_8);
        assertNotNull(result);
        assertTrue(jsonResult.contains(accessToken));
        assertTrue(jsonResult.contains(newRefreshToken));
        verify(redisService).set(eq("refresh:" + refreshToken), eq("true"));
    }

    @Test
    void getBytes_refreshPath_unauthorizedException() {
        // Given
        String path = "/api/auths/refresh";
        when(redisService.get("refresh:" + refreshToken)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CUnAuthorizedException.class, () -> authService.getBytes(refreshToken, path));
        verify(jwtUtil, never()).getUserId(anyString());
    }
}