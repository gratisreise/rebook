package com.example.rebookgateway.services;

import com.example.rebookgateway.feigns.KeycloakClient;
import com.example.rebookgateway.model.UserInfo;
import com.example.rebookgateway.utils.JwtUtil;
import com.example.rebookgateway.utils.KeycloakJwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final KeycloakClient keycloakClient;
    private final KeycloakJwtUtil keycloakJwtUtil;
    private final JwtUtil jwtUtil;

    //유저정보 생성
    public UserInfo getUserInfo(String code)
        throws UnsupportedEncodingException, JsonProcessingException {
        String accessToken = getKeycloakToken(code);
        return keycloakJwtUtil.getUserInfo(accessToken);
    }

    //토큰요청
    private String getKeycloakToken(String code) {
        String grant_type = "authorization_code";
        String clientId = "account";
        String redirectUri = "http://localhost:3000";
        return keycloakClient
            .getKeycloakToken(grant_type, clientId, code, redirectUri)
            .getAccessToken();
    }

}
