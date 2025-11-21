package com.example.rebookauthservice.service.oauth.kako;


import com.example.rebookauthservice.annotations.OAuthServiceType;
import com.example.rebookauthservice.clients.UserClient;
import com.example.rebookauthservice.enums.Provider;
import com.example.rebookauthservice.model.dto.OAuthRequest;
import com.example.rebookauthservice.model.dto.oauth.OAuthUserInfo;
import com.example.rebookauthservice.repository.AuthRepository;
import com.example.rebookauthservice.service.oauth.AbstractOAuthService;
import com.example.rebookauthservice.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@OAuthServiceType(Provider.KAKAO)
public class KakaoOAuthService extends AbstractOAuthService {


    @Value("${oauth2.client.kakao.client-id}")
    private String clientId;

    @Value("${oauth2.client.kakao.client-secret}")
    private String clientSecret;

    @Value("${oauth2.client.kakao.redirect-uri}")
    private String redirectUri;

    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoUserClient kakaoUserClient;


    public KakaoOAuthService(
        UserClient userClient,
        JwtUtil jwtUtil,
        AuthRepository authRepository,
        KakaoTokenClient kakaoTokenClient,
        KakaoUserClient kakaoUserClient
    ){
        super(userClient, jwtUtil, authRepository);
        this.kakaoTokenClient = kakaoTokenClient;
        this.kakaoUserClient = kakaoUserClient;
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
