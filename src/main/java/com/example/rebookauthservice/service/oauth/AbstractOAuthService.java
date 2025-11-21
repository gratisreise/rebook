package com.example.rebookauthservice.service.oauth;

import com.example.rebookauthservice.clients.UserClient;
import com.example.rebookauthservice.model.dto.OAuthRequest;
import com.example.rebookauthservice.model.dto.TokenResponse;
import com.example.rebookauthservice.model.dto.oauth.OAuthUserInfo;
import com.example.rebookauthservice.repository.AuthRepository;
import com.example.rebookauthservice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractOAuthService implements OAuthService{

    protected final UserClient userClient;
    protected final JwtUtil jwtUtil;
    protected final AuthRepository authRepository;

    @Override
    public TokenResponse login(OAuthRequest request){
        //OAuthServer 접근토큰 요청
        String accessToken = getAccessToken(request);

        //유저정보 요청
        OAuthUserInfo userInfo = getUserInfo(accessToken);

        //유저서비스 유저생성 요청
        String userId = createOrGetUser(userInfo);

        String jwtToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        return new TokenResponse(jwtToken, refreshToken);
    }


    protected String setBearerAuth(String accessToken){
        return "Bearer " + accessToken;
    }

}
