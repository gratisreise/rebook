package com.example.rebookuserservice.feigns;


import com.example.rebookuserservice.model.KeycloakRequest;
import com.example.rebookuserservice.model.KeycloakResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "keycloak", url = "http://keycloak:8080/realms/master/protocol/openid-connect")
public interface KeycloakClient{
    @PostMapping(value="/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KeycloakResponse getKeycloakToken(@ModelAttribute KeycloakRequest keycloakRequest);
}
