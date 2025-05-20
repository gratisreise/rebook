package com.example.rebookuserservice.feigns;


import com.example.rebookuserservice.model.KeycloakRequest;
import com.example.rebookuserservice.model.KeycloakResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "keycloak", url = "http://localhost:8081/realms/master/protocol/openid-connect")
public interface KeycloakClient{
    @PostMapping(value="/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KeycloakResponse getKeycloakToken(@ModelAttribute KeycloakRequest keycloakRequest);
}
