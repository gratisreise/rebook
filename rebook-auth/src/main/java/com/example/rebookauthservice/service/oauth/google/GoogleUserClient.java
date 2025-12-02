package com.example.rebookauthservice.service.oauth.google;


import com.example.rebookauthservice.model.dto.oauth.google.GoogleUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "googleUser", url = "https://openidconnect.googleapis.com")
public interface GoogleUserClient {
    @GetMapping("/v1/userinfo")
    GoogleUserInfo getUserInfo(@RequestHeader("Authorization") String bearerToken);
}
