package com.example.rebookauthservice.service;

import com.example.rebookauthservice.exception.AuthUserDataMissedException;
import com.example.rebookauthservice.exception.CMissingDataException;
import com.example.rebookauthservice.model.entity.AuthUser;
import com.example.rebookauthservice.repository.AuthRepository;
import com.example.rebookauthservice.utils.JwtUtil;
import com.rebook.passport.HmacUtil;
import com.rebook.passport.PassportProto;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassportService {

    private final JwtUtil jwtUtil;
    private final AuthRepository authRepository;
    private final HmacUtil hmacUtil;


    @Value("${passport.validity}")
    private long EXPIRATION;

    //패스포트 발급
    public String issuePassport(String token) {
        if(token == null || token.isBlank()){
            throw new CMissingDataException("토큰이 비어있습니다.");
        }
        String userId = jwtUtil.getAccessUserId(token);

        return generatePassport(userId);
    }

    private String generatePassport(String userId){
        //1) 유저 검증
        AuthUser user = authRepository.findByUserId(userId)
            .orElseThrow(AuthUserDataMissedException::new);

        long now = Instant.now().getEpochSecond();


        // 2) unsigned passport 생성 (proto 기반)
        PassportProto.Passport unsigned = PassportProto.Passport.newBuilder()
            .setPassportId(UUID.randomUUID().toString())
            .setUserId(userId)
            .setIssuedAt(now)
            .setExpiresAt(now + EXPIRATION)
            .build();

        // 3) passport 서명 및 base64변환
        byte[] rawData = unsigned.toByteArray();
        return hmacUtil.sign(rawData);
    }
}
