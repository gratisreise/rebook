package com.example.rebookuserservice.controller;

import com.example.rebookuserservice.common.ResponseService;
import com.example.rebookuserservice.common.SingleResult;
import com.example.rebookuserservice.model.LoginRequest;
import com.example.rebookuserservice.model.RefreshRequest;
import com.example.rebookuserservice.model.RefreshResponse;
import com.example.rebookuserservice.model.TokenResponse;
import com.example.rebookuserservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auths")
@Tag(name = "인증/인가api")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    @Operation(summary = "테스트")
    public String test(){
        return "this is test";
    }


    @PostMapping("/login")
    @Operation(summary = "로그인")
    public SingleResult<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseService.getSingleResult(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    @Operation(summary = "리프레쉬")
    public SingleResult<RefreshResponse> refresh(@RequestBody RefreshRequest refreshToken){
        return ResponseService.getSingleResult(authService.refresh(refreshToken.getRefreshToken()));
    }
}
