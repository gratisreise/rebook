package com.example.rebookuserservice.service;

import com.example.rebookuserservice.feigns.KeycloakClient;
import lombok.RequiredArgsConstructor;
import org.hibernate.grammars.hql.HqlParser.IsDistinctFromPredicateContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final Keycloak keycloak;

    private final String realm = "master";

    public void updateUserPassword(String userId, String newPassword) {
        // 1. CredentialRepresentation 객체 생성
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(false);

        // 2. UserResource를 통해 비밀번호 변경
        RealmResource realmResource = keycloak.realm(realm);
        UserResource userResource = realmResource.users().get(userId);
        userResource.resetPassword(credential);
    }

    public void deleteUser(String userId) {
        keycloak.realm(realm).users().delete(userId);
    }

}
