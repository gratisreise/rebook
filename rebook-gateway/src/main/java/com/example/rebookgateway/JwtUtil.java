package com.example.rebookgateway;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {

    private final SecretKey key;

    public JwtUtil(@Value("${jwt.secret}") String key) throws DecoderException {
//        byte[] keyBytes = Hex.decodeHex(key.toCharArray());
        log.info("jwt.secret: {}", key);
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String getUserId(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    //토큰검증
    public boolean validateToken(String token) {
        try {
            log.info("Validating token: {}", token);
            // 파싱 및 서명 검증 (만료, 변조 여부 확인)
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }


}