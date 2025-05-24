package com.example.rebookuserservice.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
            .serverUrl("http://keycloak:8080") // Keycloak 서버 주소
            .realm("master") // 관리할 Realm 이름
            .clientId("admin-cli") // 관리 권한이 있는 clientId
            .grantType(OAuth2Constants.PASSWORD)
            .username("admin") // Keycloak 관리자 계정
            .password("admin")
            .build();
    }

}
