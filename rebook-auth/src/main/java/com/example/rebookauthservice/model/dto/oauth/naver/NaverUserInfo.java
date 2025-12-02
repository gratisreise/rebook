package com.example.rebookauthservice.model.dto.oauth.naver;

public record NaverUserInfo(
    String resultcode,
    String message,
    NaverResponse response
) { }
