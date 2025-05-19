package com.example.rebookuserservice.controller;

import com.example.rebookuserservice.common.CommonResult;
import com.example.rebookuserservice.common.ResponseService;
import com.example.rebookuserservice.common.SingleResult;
import com.example.rebookuserservice.model.LoginRequest;
import com.example.rebookuserservice.model.TokenResponse;
import com.example.rebookuserservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auths/login")
    public SingleResult<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseService.getSingleResult(authService.login(loginRequest));
    }

    @PostMapping("/api/auths/refresh")
    public SingleResult<TokenResponse> refresh(@RequestHeader String refreshToken){

        return ResponseService.getSingleResult(authService.refresh(refreshToken));
    }
}
