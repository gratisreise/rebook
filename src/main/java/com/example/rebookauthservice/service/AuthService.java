package com.example.rebookauthservice.service;

import com.example.rebookauthservice.clients.UserClient;
import com.example.rebookauthservice.exception.CDuplicatedDataException;
import com.example.rebookauthservice.exception.CMissingDataException;
import com.example.rebookauthservice.model.dto.LoginRequest;
import com.example.rebookauthservice.model.dto.OAuthRequest;
import com.example.rebookauthservice.model.dto.RefreshResponse;
import com.example.rebookauthservice.model.dto.SignUpRequest;
import com.example.rebookauthservice.model.dto.TokenResponse;
import com.example.rebookauthservice.model.dto.UsersCreateRequest;
import com.example.rebookauthservice.model.entity.AuthUser;
import com.example.rebookauthservice.repository.AuthRepository;
import com.example.rebookauthservice.utils.JwtUtil;
import com.example.rebookauthservice.utils.RedisUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
    private final RedisUtil redisUtil;

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
        //유저검증
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String userId = authRepository.findByUsername(request.username())
            .orElseThrow(CMissingDataException::new)
            .getUserId();

        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);

        //리프레쉬 토큰 저장
        redisUtil.save(userId, refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    //토큰 리프레쉬
    public RefreshResponse refresh(String refreshToken) {
        String userId = jwtUtil.getRefreshUserId(refreshToken);
        String accessToken = jwtUtil.createAccessToken(userId);
        return new RefreshResponse(accessToken);
    }

}
