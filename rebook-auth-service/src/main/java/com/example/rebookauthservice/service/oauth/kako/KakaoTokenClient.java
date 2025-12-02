package com.example.rebookauthservice.service.oauth.kako;


import com.example.rebookauthservice.model.dto.oauth.kako.KakaoTokenResponse;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "kakaoToken", url="https://kauth.kakao.com")
public interface KakaoTokenClient {
    @PostMapping( value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponse getAccessToken(@RequestBody Map<String, ?> formParams);
}
