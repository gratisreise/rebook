package com.example.rebookchatservice.repository;

import com.example.rebookchatservice.model.entity.ChatMessage;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    Page<ChatMessage> findByRoomId(Long roomId, Pageable pageable);
    Optional<ChatMessage> findFirstByRoomIdOrderBySendAtDesc(Long roomId);
}
