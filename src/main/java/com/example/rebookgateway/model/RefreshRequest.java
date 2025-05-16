package com.example.rebookgateway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
// 리프레쉬요청에 따라 토큰을 넘기는 용도
public class RefreshRequest {
    private String accessToken;
    private String refreshToken;
}
