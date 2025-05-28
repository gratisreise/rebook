package com.example.rebookuserservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UsersUpdateRequest {
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 3, max = 20)
    private String nickname;

    private MultipartFile profileImage;
}
