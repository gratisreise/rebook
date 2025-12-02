package com.example.rebookauthservice.model.dto;

import com.example.rebookauthservice.annotation.Password;
import com.example.rebookauthservice.enums.Provider;
import com.example.rebookauthservice.enums.Role;
import com.example.rebookauthservice.model.entity.AuthUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;


public record SignUpRequest(
    @NotBlank
    @Length(min=3, max=40)
    String username,

    @Password
    String password,

    @NotBlank
    @Length(min=3, max=15)
    String nickname,

    @NotBlank
    @Email
    String email
) {

    public AuthUser toEntity(String userId, PasswordEncoder encoder){
        return AuthUser.builder()
            .userId(userId)
            .username(username)
            .password(encoder.encode(password))
            .role(Role.USER) // 로그인에서 가입가능한 유저는 admin이 없다
            .provider(Provider.LOCAL) //
            .build();
    }
}
