package com.example.rebookgateway.services;

import com.example.rebookgateway.feigns.KeycloakClient;
import com.example.rebookgateway.model.UserInfo;
import com.example.rebookgateway.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final KeycloakClient keycloakClient;
    private final JwtUtil jwtUtil;

    private String keycloakToken(String code) {
        String grant_type = "authorization_code";
        String clientId = "account";
        String redirectUri = "http://localhost:3000";
        return keycloakClient
            .getKeycloakToken(grant_type, clientId, code, redirectUri)
            .getAccessToken();
    }

    public UserInfo userInfo(String code){
        String accessToken = keycloakToken(code);


        return new UserInfo();
    }




}
