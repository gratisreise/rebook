package com.example.rebookauthservice.service.oauth.naver;


import com.example.rebookauthservice.model.dto.oauth.naver.NaverUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naverUser", url = "https://openapi.naver.com")
public interface NaverUserClient {
    @GetMapping("/v1/nid/me")
    NaverUserInfo getUserInfo(@RequestHeader("Authorization") String bearerToken);
}
