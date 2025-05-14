package com.example.rebookgateway.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long accessValidity;
    private final long refreshValidity;

    public JwtUtil(
        @Value("${jwt.secret}") String key,
        @Value("${jwt.access-expiration}") long accessTokenExpiration,
        @Value("${jwt.refresh-expiration}") long refreshTokenExpiration
    ) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
        this.accessValidity = accessTokenExpiration;
        this.refreshValidity = refreshTokenExpiration;
    }

    public String createAccessToken(String subject, long memberId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessValidity);

        return Jwts.builder()
            .subject(subject)
            .claim("memberId", memberId)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key, Jwts.SIG.HS512) // 0.12.x 버전의 새로운 서명 방식
            .compact();
    }

    public String createRefreshToken(String subject, long memberId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValidity);

        return Jwts.builder()
            .subject(subject)
            .claim("memberId", memberId)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key, Jwts.SIG.HS512) // 0.12.x 버전의 새로운 서명 방식
            .compact();
    }




}