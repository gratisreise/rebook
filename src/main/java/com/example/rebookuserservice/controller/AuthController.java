package com.example.rebookuserservice.controller;

import com.example.rebookuserservice.common.CommonResult;
import com.example.rebookuserservice.common.SingleResult;
import com.example.rebookuserservice.model.TokenResponse;
import com.example.rebookuserservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auths/login")
    public SingleResult<TokenResponse> login(){

        return null;
    }

    @PostMapping("/api/auths/refresh")
    public SingleResult<TokenResponse> refresh(){

        return null;
    }
}
