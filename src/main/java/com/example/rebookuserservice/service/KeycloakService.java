package com.example.rebookuserservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakService {
    private final Keycloak keycloak;

    private final String realm = "master";

    public void updatePassword(String userId, String newPassword) {
        // 1. CredentialRepresentation 객체 생성
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(false);
        log.info("complete create credential");
        log.info("creating user {}", userId);
        log.info("updating password {}", newPassword);

        // 2. UserResource를 통해 비밀번호 변경
        RealmResource realmResource = keycloak.realm(realm);
        UserResource userResource = realmResource.users().get(userId);
        userResource.resetPassword(credential);
        log.info("complete update password");
    }

    public void deleteUser(String userId) {
        try{
            keycloak.realm(realm).users().delete(userId);
        } catch (RuntimeException e){
            throw new RuntimeException("Keyclaok 유저 삭제에 실패했습니다.");
        }

    }

}
