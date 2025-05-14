package com.example.rebookgateway.utils;

import com.example.rebookgateway.model.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class KeycloakJwtUtil {

    public UserInfo getUserInfo(String token)
        throws UnsupportedEncodingException, JsonProcessingException {

        // 1. JWT 분리
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("잘못된 JWT 토큰 형식입니다.");
        }

        // 2. 페이로드(Base64 URL) 디코딩
        byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
        String payloadJson = new String(decodedBytes, "UTF-8");

        // 3. JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> payload = objectMapper.readValue(payloadJson, Map.class);

        // 4. 정보 추출
        String sub = (String) payload.get("sub");
        String email = (String) payload.get("email");
        String username = (String) payload.get("preferred_username");

        // roles: resource_access.account.roles
        String role = "";
        Map<String, Object> resourceAccess = (Map<String, Object>) payload.get("resource_access");
        if (resourceAccess != null && resourceAccess.containsKey("account")) {
            Map<String, Object> account = (Map<String, Object>) resourceAccess.get("account");
            if (account != null && account.containsKey("roles")) {
                List<String> roles = (List<String>) account.get("roles");
                if (roles.contains("admin")) {
                    role = roles.stream().findFirst()
                        .orElseThrow(IllegalArgumentException::new);
                }
            }
        }

        return UserInfo.builder()
            .userId(sub)
            .username(username)
            .email(email)
            .role(role)
            .build();
    }
}
