package com.example.rebookuserservice.utils;

import com.example.rebookuserservice.exception.CMissingDataException;
import com.example.rebookuserservice.model.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakJwtUtil {


    private final PublicKey key;

    public KeycloakJwtUtil(@Value("${jwt.keycloak}") String key)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
        if(key == null ||  !key.matches("^[A-Za-z0-9+/=]+$")){
            throw new CMissingDataException(key + " is not a valid keycloak key");
        }
        byte[] decodedKey = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.key = keyFactory.generatePublic(spec);
    }

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
        try{
            @SuppressWarnings("unchecked")
            Map<String, Object> resourceAccess = (Map<String, Object>) claims.get("resource_access");
            @SuppressWarnings("unchecked")
            Map<String, Object> account = (Map<String, Object>) resourceAccess.get("account");
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) account.get("roles");
            return roles.stream().findFirst()
                .orElseThrow(CMissingDataException::new);
        } catch(ClassCastException e){
            throw new ClassCastException("형식에 맞지 않는 타입변환입니다.");
        }
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
