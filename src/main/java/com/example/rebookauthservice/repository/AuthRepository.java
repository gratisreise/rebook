package com.example.rebookauthservice.repository;

import com.example.rebookauthservice.model.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthUser, Long> {

}
