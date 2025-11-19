package com.example.rebookauthservice.controller;


import com.example.rebookauthservice.common.CommonResult;
import com.example.rebookauthservice.common.ResponseService;
import com.example.rebookauthservice.model.dto.SignUpRequest;
import com.example.rebookauthservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    //회원가입
    @PostMapping("/sign-up")
    public CommonResult signUp(@RequestBody SignUpRequest request){
        authService.signUp(request);
        return ResponseService.getSuccessResult();
    }

    //로그인

    //소셜로그인

    //리프레쉬

    //패스포트생성

}
