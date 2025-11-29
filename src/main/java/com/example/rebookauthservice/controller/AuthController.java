package com.example.rebookauthservice.controller;


import com.example.rebookauthservice.common.CommonResult;
import com.example.rebookauthservice.common.ResponseService;
import com.example.rebookauthservice.common.SingleResult;
import com.example.rebookauthservice.model.dto.LoginRequest;
import com.example.rebookauthservice.model.dto.OAuthRequest;
import com.example.rebookauthservice.model.dto.RefreshRequest;
import com.example.rebookauthservice.model.dto.RefreshResponse;
import com.example.rebookauthservice.model.dto.SignUpRequest;
import com.example.rebookauthservice.model.dto.TokenResponse;
import com.example.rebookauthservice.repository.AuthRepository;
import com.example.rebookauthservice.service.AuthService;
import com.example.rebookauthservice.service.oauth.OAuthService;
import com.example.rebookauthservice.service.oauth.OAuthServiceFactory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final OAuthServiceFactory oAuthServiceFactory;
    private final AuthRepository authRepository;


    //test
    @GetMapping
    public String test(){
        return authRepository.findById(2L).toString();
    }

    //회원가입
    @PostMapping("/sign-up")
    public CommonResult signUp(@Valid @RequestBody SignUpRequest request){
        authService.signUp(request);
        return ResponseService.getSuccessResult();
    }

    //로그인
    @PostMapping("/login")
    public SingleResult<TokenResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseService.getSingleResult(authService.login(request));
    }

    //소셜로그인
    @GetMapping("/oauth/login")
    public SingleResult<TokenResponse> socialLogin(@Valid @RequestBody OAuthRequest request){
        OAuthService oauthService = oAuthServiceFactory.getOAuthService(request.provider());
        return ResponseService.getSingleResult(oauthService.login(request));
    }

    //리프레쉬
    @GetMapping("/refresh")
    public SingleResult<RefreshResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        return ResponseService.getSingleResult(authService.refresh(request.refreshToken()));
    }

}
