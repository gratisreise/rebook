package com.example.rebookauthservice.service.oauth;


import com.example.rebookauthservice.model.dto.OAuthRequest;
import com.example.rebookauthservice.model.dto.TokenResponse;
import com.example.rebookauthservice.model.dto.oauth.OAuthUserInfo;

public interface OAuthService {
    TokenResponse login(OAuthRequest oAuthRequest);
    String getAccessToken(OAuthRequest oAuthRequest);
    OAuthUserInfo getUserInfo(String accessToken);
    String createOrGetUser(OAuthUserInfo userInfo);
}
