package com.example.rebookgateway.utils;

import com.example.rebookgateway.feigns.KeycloakClient;
import com.example.rebookgateway.model.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakJwtUtil {
    private final KeycloakClient keycloakClient;
    
    public UserInfo getUserInfo(String code)
        throws UnsupportedEncodingException, JsonProcessingException {
        String token = getKeycloakToken(code);

        //payload 생성
        Map<String, Object> payload = getPayload(token);

        // 정보 추출
        String sub = (String) payload.get("sub");
        String email = (String) payload.get("email");
        String username = (String) payload.get("preferred_username");

        // role 추출
        String role = getRole(payload);

        return UserInfo.builder()
            .userId(sub)
            .username(username)
            .email(email)
            .role(role)
            .build();
    }

    private Map<String, Object> getPayload(String token)
        throws UnsupportedEncodingException, JsonProcessingException {
        // 1. JWT 분리
        String payloadPart = getPayloadPart(token);

        // 2. 페이로드(Base64 URL) 디코딩
        String payloadJson = getPayloadJson(payloadPart);

        // 3. JSON 파싱
        return getStringObjectMap(payloadJson);
    }

    private  String getRole(Map<String, Object> payload) {

        Map<String, Object> resourceAccess = (Map<String, Object>) payload.get("resource_access");
        if (resourceAccess == null && !resourceAccess.containsKey("account")){
            throw new IllegalArgumentException("계정이 존재하지 않습니다.");
        }
        Map<String, Object> account = (Map<String, Object>) resourceAccess.get("account");
        if (account == null && !account.containsKey("roles")) {
            throw new IllegalArgumentException("역할지 존재하지 않습니다.");
        }
        List<String> roles = (List<String>) account.get("roles");
        if(roles.isEmpty()) {
            throw new IllegalArgumentException("할당된 역할이 없습니다.");
        }

        return roles.stream().findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private Map<String, Object> getStringObjectMap(String payloadJson)
        throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(payloadJson, Map.class);
    }

    private String getPayloadJson(String payloadPart) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(payloadPart);
        return new String(decodedBytes, "UTF-8");
    }

    private String getPayloadPart(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("잘못된 JWT 토큰 형식입니다.");
        }
        return parts[1];
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
