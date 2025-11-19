package com.example.rebookauthservice.service;

import com.example.rebookauthservice.clients.UserClient;
import com.example.rebookauthservice.exception.CDuplicatedDataException;
import com.example.rebookauthservice.exception.CMissingDataException;
import com.example.rebookauthservice.model.dto.LoginRequest;
import com.example.rebookauthservice.model.dto.SignUpRequest;
import com.example.rebookauthservice.model.dto.TokenResponse;
import com.example.rebookauthservice.model.dto.UsersCreateRequest;
import com.example.rebookauthservice.model.entity.AuthUser;
import com.example.rebookauthservice.repository.AuthRepository;
import com.example.rebookauthservice.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final UserClient userClient;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(SignUpRequest request) {
        //중복검증
        if(authRepository.existsByUsername(request.username())){
            throw new CDuplicatedDataException("존재하는 아이디입니다.");
        }

        //User 생성 요청
        UsersCreateRequest req = UsersCreateRequest.from(request);
        String userId = userClient.createUser(req);

        //AuthUser 생성 및 저장
        AuthUser user = request.toEntity(userId, encoder);

        authRepository.save(user);
    }

    //로그인
    public TokenResponse login(LoginRequest request){
        //유저 검증
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String userId = authRepository.findByUsername(request.username())
            .orElseThrow(CMissingDataException::new)
            .getUserId();

        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        return new TokenResponse(accessToken, refreshToken);
    }

    //소셜로그인

}
