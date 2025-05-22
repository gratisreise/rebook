package com.example.rebookuserservice.repository;

import com.example.rebookuserservice.model.entity.UserTrading;
import com.example.rebookuserservice.model.entity.compositekey.UserTradingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTradingRepository extends JpaRepository<UserTrading, UserTradingId> {

    Page<UserTrading> findAllUserTradingIdUserId(String userId, Pageable pageable);
}
