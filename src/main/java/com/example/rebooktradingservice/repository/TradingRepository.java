package com.example.rebooktradingservice.repository;

import com.example.rebooktradingservice.model.entity.Trading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingRepository extends JpaRepository<Trading, Long> {

}
