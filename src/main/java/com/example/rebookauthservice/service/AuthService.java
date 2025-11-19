package com.example.rebookauthservice.service;

import com.example.rebookauthservice.clients.UserClient;
import com.example.rebookauthservice.common.ResultCode;
import com.example.rebookauthservice.exception.CDuplicatedDataException;
import com.example.rebookauthservice.model.dto.SignUpRequest;
import com.example.rebookauthservice.model.dto.UsersCreateRequest;
import com.example.rebookauthservice.model.dto.UsersResponse;
import com.example.rebookauthservice.model.entity.AuthUser;
import com.example.rebookauthservice.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Transactional
    public void signUp(SignUpRequest request) {
        //중복검증
        if(authRepository.existsByUsername(request.getUsername())){
            throw new CDuplicatedDataException(ResultCode.DUPLICATED_USER.getMsg());
        }

        //User 생성 요청
        UsersCreateRequest req = UsersCreateRequest.from(request);
        String userId = userClient.createUser(req);

        //AuthUser 생성 및 저장
        AuthUser user = request.toEntity(userId, encoder);

        authRepository.save(user);
    }

    //로그인

    //소셜로그인
}
