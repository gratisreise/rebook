package com.example.rebooktradingservice.repository;

import com.example.rebooktradingservice.model.entity.TradingUser;
import com.example.rebooktradingservice.model.entity.compositekey.TradingUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingUserRepository extends JpaRepository<TradingUser, TradingUserId> {

}
