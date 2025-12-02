package com.example.rebookauthservice.model.dto.oauth.google;

import com.example.rebookauthservice.enums.Provider;
import com.example.rebookauthservice.model.dto.oauth.OAuthUserInfo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GoogleOAuthUserInfo implements OAuthUserInfo {
    private final GoogleUserInfo userInfo;

    @Override
    public String getId() {
        return userInfo.id();
    }

    @Override
    public String getName() {
        return Provider.GOOGLE.name() + userInfo.id();
    }

    @Override
    public String getImageUrl() {
        return userInfo.picture();
    }
}
