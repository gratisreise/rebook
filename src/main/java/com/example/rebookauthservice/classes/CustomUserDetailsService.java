package com.example.rebookauthservice.classes;

import com.example.rebookauthservice.exception.CMissingDataException;
import com.example.rebookauthservice.model.entity.AuthUser;
import com.example.rebookauthservice.repository.AuthRepository;
import com.example.rebookauthservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private AuthRepository authRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = authRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("아이디나 비밀번호가 틀립니다."));
        return new CustomUserDetails(user);
    }
}
