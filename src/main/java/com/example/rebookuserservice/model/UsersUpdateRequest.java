package com.example.rebookuserservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UsersUpdateRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{8,}$", message = "8자 이상, 영어 대문자, 소문자 모두 포함해야함.")
    private String password;

    @NotBlank
    @Length(min = 3, max = 20)
    private String nickname;

    @NotNull
    private MultipartFile multipartFile;

}
