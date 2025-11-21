package com.example.rebookuserservice.model;

import com.example.rebookuserservice.enums.Provider;
import com.example.rebookuserservice.model.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UsersCreateRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Length(min = 3, max = 100)
    private String nickname;

    public Users toEntity(String image){
        return Users.builder()
            .email(email)
            .nickname(nickname)
            .profileImage(image)
            .provider(Provider.LOCAL)
            .build();
    }


}
