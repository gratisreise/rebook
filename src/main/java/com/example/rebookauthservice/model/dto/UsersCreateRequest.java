package com.example.rebookauthservice.model.dto;

import com.example.rebookauthservice.enums.Role;
import com.example.rebookauthservice.model.entity.AuthUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
public class UsersCreateRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 3, max = 100)
    private String nickname;


    public static UsersCreateRequest from(SignUpRequest request){
        return UsersCreateRequest.builder()
            .email(request.getEmail())
            .nickname(request.getNickname())
            .build();
    }
}
