package com.example.rebookuserservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{8,}$", message = "8자 이상, 영어 대문자, 소문자 모두 포함해야함.")
    private String password;
}
