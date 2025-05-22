package com.example.rebookuserservice.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {
    @NotBlank
    private String password;
}
