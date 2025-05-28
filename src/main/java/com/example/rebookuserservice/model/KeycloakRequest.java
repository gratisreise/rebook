package com.example.rebookuserservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KeycloakRequest {
     private String grant_type = "authorization_code";
     private String code ;
     private String client_id = "account";
     private String redirect_uri = "http://localhost:3001";
     public KeycloakRequest(String code) {
         this.code = code;
     }
}
