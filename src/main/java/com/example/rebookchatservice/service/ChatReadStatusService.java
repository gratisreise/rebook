package com.example.rebookchatservice.service;

import com.example.rebookchatservice.model.entity.ChatMessage;
import com.example.rebookchatservice.model.entity.ChatReadStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatReadStatusService {

    private final ChatReadStatusReader chatReadStatusReader;
    private final ChatMessageReader chatMessageReader;

    @Transactional
    public void patchLastRead(Long roomId, String userId) {
        ChatReadStatus readStatus = chatReadStatusReader.findById(roomId, userId);
        LocalDateTime lastRead = chatMessageReader.lastMessageTime(roomId);
        readStatus.setLastRead(lastRead);
    }
}
