package com.example.rebookuserservice.service;

import com.example.rebookuserservice.feigns.KeycloakClient;
import com.example.rebookuserservice.model.KeycloakResponse;
import com.example.rebookuserservice.model.TokenResponse;
import com.example.rebookuserservice.model.User;
import com.example.rebookuserservice.model.UserInfo;
import com.example.rebookuserservice.repository.UserRepository;
import com.example.rebookuserservice.utils.JwtUtil;
import com.example.rebookuserservice.utils.KeycloakJwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Value("${aws.base-image}")
    private String baseImageUrl;

    @Transactional
    public TokenResponse login(String code) {
        //키클록에 토큰요청
        String keycloakToken = getKeycloakToken(code);

        //토큰에서 정보추출
        UserInfo userInfo = keycloakJwtUtil.getUserInfo(keycloakToken);
        String userId = userInfo.getUserId();

        //유저DB에 있는 유저인지 확인(X: UserDB에 저장)
        if(!userRepository.existsById(userId)){
            User user = new User(userInfo);
            user.setNickname(basicName + userId);
            user.setProfileImage(baseImageUrl);
            userRepository.save(user);
        }

        //토큰생성 후 반환
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        cacheRefreshToken(refreshToken);

        return new TokenResponse(accessToken, refreshToken);
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
