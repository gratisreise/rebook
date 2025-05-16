package com.example.rebookgateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 유저서비스에 정보를 넘기는 용도
public class UserInfo {
    private String userId;
    private String username;
    private String email;
    private String role;
    private String accessToken;
    private String refreshToken;
}
