package com.example.rebookgateway.utils;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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

    public String createAccessToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessValidity);

        return Jwts.builder()
            .subject(userId)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key, Jwts.SIG.HS512) // 0.12.x 버전의 새로운 서명 방식
            .compact();
    }

    public String createRefreshToken(String userId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValidity);

        return Jwts.builder()
            .subject(userId)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key, Jwts.SIG.HS512) // 0.12.x 버전의 새로운 서명 방식
            .compact();
    }

    public String getUserId(String token){
        return Jwts.parser()
            .verifyWith(key)   // 0.12.x 버전의 새로운 검증 방식, 유요한지?, 만료되었는지
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    //토큰검증
    public boolean validateToken(String token) {
        try {
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