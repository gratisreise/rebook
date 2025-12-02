package com.example.rebookauthservice.model.dto.oauth.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserInfo(
    @JsonProperty("sub") String id,
    String email,
    String name,
    String picture
) { }
