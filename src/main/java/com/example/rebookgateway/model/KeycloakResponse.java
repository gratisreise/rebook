package com.example.rebookgateway.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KeycloakResponse {
    private String accessToken;
    private int expiresIn;
    private int refreshExpiresIn;
    private String refreshToken;
    private String tokenType;
    @JsonProperty("not-before-policy")
    private int notBeforePolicy;
    private String sessionState;
    private String scope;
}
