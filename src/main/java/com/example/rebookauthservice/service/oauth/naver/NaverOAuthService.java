package com.example.rebookauthservice.service.oauth.naver;


import com.example.rebookauthservice.annotations.OAuthServiceType;
import com.example.rebookauthservice.clients.UserClient;
import com.example.rebookauthservice.enums.Provider;
import com.example.rebookauthservice.exception.CMissingDataException;
import com.example.rebookauthservice.model.dto.OAuthRequest;
import com.example.rebookauthservice.model.dto.oauth.OAuthUserInfo;
import com.example.rebookauthservice.model.dto.oauth.naver.NaverTokenResponse;
import com.example.rebookauthservice.model.dto.oauth.naver.NaverUserInfo;
import com.example.rebookauthservice.repository.AuthRepository;
import com.example.rebookauthservice.service.oauth.AbstractOAuthService;
import com.example.rebookauthservice.service.oauth.kako.KakaoTokenClient;
import com.example.rebookauthservice.service.oauth.kako.KakaoUserClient;
import com.example.rebookauthservice.utils.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@OAuthServiceType(Provider.NAVER)
public class NaverOAuthService extends AbstractOAuthService {


    @Value("${oauth2.client.naver.client-id}")
    private String clientId;

    @Value("${oauth2.client.naver.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.naver.redirect-uri}")
    private String redirectUri;

    private final NaverTokenClient naverTokenClient;
    private final NaverUserClient naverUserClient;

    public NaverOAuthService(
        UserClient userClient,
        JwtUtil jwtUtil,
        AuthRepository authRepository,
        NaverTokenClient naverTokenClient,
        NaverUserClient naverUserClient
    ){
        super(userClient, jwtUtil, authRepository);
        this.naverTokenClient = naverTokenClient;
        this.naverUserClient = naverUserClient;
    }


    @Override
    public String getAccessToken(OAuthRequest oAuthRequest) {
        return "";
    }

    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        return null;
    }

    @Override
    public String createOrGetUser(OAuthUserInfo userInfo) {
        return "";
    }
}
