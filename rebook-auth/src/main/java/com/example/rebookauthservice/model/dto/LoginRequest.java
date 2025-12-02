package com.example.rebookauthservice.model.dto;


import com.example.rebookauthservice.annotation.Password;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
    @NotBlank
    String username,

    @Password
    String password
){

}
