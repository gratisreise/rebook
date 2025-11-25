package com.example.rebookauthservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {

    //성공
    SUCCESS(200, "success"),


    //실패
    FAILED(-100, "failed"),
    PASSWORD_NOT_VALID(-101, "패스워드가 형식에 맞지 않습니다."),
    DATA_MISSED(-102, "데이터가 존재하지 않습니다."),
    DATA_DUPLICATED(-103, "데이터가 중복됩니다."),
    DATA_INVALID(-104, "데이터가 유효하지 않습니다."),
    DUPLICATED_USER(-105, "중복되는유저입니다."),
    AUTH_USER_DATA_MISSED(-106, "Auth 유저를 가져오지 못했습니다."),
    PASSWORD_UNMATCHED(-106, "아이디 또는 비밀번호가 틀립니다.");
    private int code;
    private String msg;

}
