package com.example.rebookuserservice.repository;

import com.example.rebookuserservice.model.UserTrading;
import com.example.rebookuserservice.model.compositekey.UserTradingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTradingRepository extends JpaRepository<UserTrading, UserTradingId> {

}
