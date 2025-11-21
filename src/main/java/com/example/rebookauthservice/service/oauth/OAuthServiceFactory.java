package com.example.rebookauthservice.service.oauth;


import com.example.rebookauthservice.annotations.OAuthServiceType;
import com.example.rebookauthservice.enums.Provider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OAuthServiceFactory {
    private final Map<Provider, OAuthService> serviceMap = new HashMap<>();

    public OAuthServiceFactory(List<OAuthService> services){
        for(OAuthService service : services){
            OAuthServiceType type = service.getClass().getAnnotation(OAuthServiceType.class);
            if(type != null){
                serviceMap.put(type.value(), service);
            }
        }
    }

    public OAuthService getOAuthService(Provider provider){
        return serviceMap.get(provider);
    }

}
