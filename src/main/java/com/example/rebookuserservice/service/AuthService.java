package com.example.rebookuserservice.service;

import com.example.rebookuserservice.feigns.KeycloakClient;
import com.example.rebookuserservice.model.LoginRequest;
import com.example.rebookuserservice.model.TokenResponse;
import com.example.rebookuserservice.model.Users;
import com.example.rebookuserservice.model.UserInfo;
import com.example.rebookuserservice.repository.UserRepository;
import com.example.rebookuserservice.utils.JwtUtil;
import com.example.rebookuserservice.utils.KeycloakJwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final KeycloakClient keycloakClient;
    private final KeycloakJwtUtil keycloakJwtUtil;
    private final RedisService redisService;
    private final JwtUtil jwtUtil;

    private String basicName = "닉네임";

    @Value("${aws.basic}")
    private String baseImageUrl;

    @Transactional
    public TokenResponse login(LoginRequest request) {
        String code = request.getCode();

        String keycloakToken = getKeycloakToken(code);

        UserInfo userInfo = keycloakJwtUtil.getUserInfo(keycloakToken);
        String userId = userInfo.getUserId();

        if(!userRepository.existsById(userId)){
            Users users = new Users(userInfo);
            users.setNickname(basicName + userId);
            users.setProfileImage(baseImageUrl);
            userRepository.save(users);
        }

        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        cacheRefreshToken(refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refresh(String refreshToken) {
        return new TokenResponse();
    }

    private String getKeycloakToken(String code) {
        String grantType = "authorization_code";
        String clientId = "account";
        String redirectUri = "http://localhost:3000";
        return keycloakClient.getKeycloakToken(grantType, code, clientId, redirectUri)
            .getAccessToken();
    }

    private void cacheRefreshToken(String refreshToken) {
        String refreshPrefix = "refresh:";
        redisService.get(refreshPrefix + refreshToken);
    }


}
