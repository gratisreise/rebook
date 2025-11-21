package com.example.rebookauthservice.repository;

import com.example.rebookauthservice.enums.Provider;
import com.example.rebookauthservice.model.entity.AuthUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthUser, Long> {

    Optional<AuthUser> findByUsername(String username);
    boolean existsByUsername(String username);

    Optional<AuthUser> findByProviderAndProviderId(Provider provider, String providerId);
}
