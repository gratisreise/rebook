package com.example.rebookuserservice.utils;

import com.example.rebookuserservice.exception.CMissingDataException;
import com.example.rebookuserservice.model.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakJwtUtil {

    @Value("jwt.keyclaok")
    private PublicKey key;

    public UserInfo getUserInfo(String token){
        //sub 가져오기
        String userId = getSubject(token);
        //claim가져오기
        Claims claims = getClaims(token);

        //정보 꺼내기
        String username = claims.get("preferred_username", String.class);
        String email = claims.get("email", String.class);
        String role = getRole(claims);

        return new UserInfo(userId, username, email, role);
    }

    private String getRole(Claims claims) {
        Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
        Map<String, Object> account = (Map<String, Object>) resourceAccess.get("account");
        List<String> roles = (List<String>) account.get("roles");
        return roles.stream().findFirst()
            .orElseThrow(CMissingDataException::new);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private String getSubject(String token){
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

}
