package com.example.rebookauthservice.service.oauth.naver;


import com.example.rebookauthservice.annotations.OAuthServiceType;
import com.example.rebookauthservice.clients.UserClient;
import com.example.rebookauthservice.enums.Provider;
import com.example.rebookauthservice.exception.CMissingDataException;
import com.example.rebookauthservice.model.dto.OAuthRequest;
import com.example.rebookauthservice.model.dto.OAuthUsersRequest;
import com.example.rebookauthservice.model.dto.oauth.OAuthUserInfo;
import com.example.rebookauthservice.model.dto.oauth.naver.NaverOAuthUserInfo;
import com.example.rebookauthservice.model.dto.oauth.naver.NaverTokenResponse;
import com.example.rebookauthservice.model.dto.oauth.naver.NaverUserInfo;
import com.example.rebookauthservice.model.entity.AuthUser;
import com.example.rebookauthservice.repository.AuthRepository;
import com.example.rebookauthservice.service.oauth.AbstractOAuthService;
import com.example.rebookauthservice.service.oauth.kako.KakaoTokenClient;
import com.example.rebookauthservice.service.oauth.kako.KakaoUserClient;
import com.example.rebookauthservice.utils.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    public String getAccessToken(OAuthRequest request) {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", request.code());
        params.put("state", request.state());
        params.put("redirect_uri", redirectUri);

        NaverTokenResponse response = naverTokenClient.getAccessToken(params);

        if (response == null || response.getAccessToken() == null) {
            throw new CMissingDataException("토큰 응답이 비어있습니다.");
        }

        return response.getAccessToken();
    }

    @Override
    public OAuthUserInfo getUserInfo(String accessToken) {
        NaverUserInfo userInfo = naverUserClient.getUserInfo("Bearer " + accessToken);

        if (userInfo == null || userInfo.response() == null) {
            throw new CMissingDataException("사용자 정보가 비어있습니다.");
        }

        return new NaverOAuthUserInfo(userInfo);
    }

    @Override
    public String createOrGetUser(OAuthUserInfo userInfo) {
        Provider provider = Provider.NAVER;
        String providerId = userInfo.getId();
        Optional<AuthUser> user = authRepository
            .findByProviderAndProviderId(provider, providerId);
        if(user.isPresent()){
            return user.get().getUserId();
        }
        OAuthUsersRequest request = OAuthUsersRequest.from(userInfo);
        return userClient.createUser(request);
    }
}
