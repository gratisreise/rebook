package com.example.rebookuserservice.repository;

import com.example.rebookuserservice.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
