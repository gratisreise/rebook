package com.example.rebookuserservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakResponse {
    @JsonProperty("access_token")
    private String accessToken;
}
