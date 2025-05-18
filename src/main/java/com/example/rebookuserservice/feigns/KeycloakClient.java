package com.example.rebookuserservice.feigns;


import com.example.rebookuserservice.model.KeycloakResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "keycloak", url = "http://keycloak:8081/realms/master/protocol/openid-connect")
public interface KeycloakClient{
    @PostMapping(value="/token", consumes = "application/x-www-form-urlencoded")
    KeycloakResponse getKeycloakToken(
        @RequestParam("grant_type") String grantType,
        @RequestParam("code") String code,
        @RequestParam("client_id") String clientId,
        @RequestParam("redirect_uri") String redirectUri
    );
}
