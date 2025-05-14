package com.example.rebookgateway.utils;

import com.example.rebookgateway.model.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    //id
    //username
    //email
    //role
    public UserInfo getUserInfo(String token){

        return UserInfo.builder()
            .build();
    }




}