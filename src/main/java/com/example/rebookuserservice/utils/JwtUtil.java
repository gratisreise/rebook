package com.example.rebookuserservice.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private final SecretKey key;

    @Value("${jwt.access-validity}")
    private long accessTokenValidity;

    @Value("${jwt.refresh-validity}")
    private long refreshTokenValidity;

    public JwtUtil(@Value("${jwt.secret}")String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String createAccessToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
            .subject(userId)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key, Jwts.SIG.HS512) // 0.12.x 버전의 새로운 서명 방식
            .compact();
    }

    public String createRefreshToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidity);

        return Jwts.builder()
            .subject(userId)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key, Jwts.SIG.HS512)
            .compact();
    }

    public String getUserId(String token){
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

}
