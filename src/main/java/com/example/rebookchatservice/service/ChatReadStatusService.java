package com.example.rebookchatservice.service;

import com.example.rebookchatservice.model.entity.ChatReadStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatReadStatusService {

    private final ChatReadStatusReader chatReadStatusReader;

    @Transactional
    public void patchLastRead(Long roomId) {
        ChatReadStatus readStatus = chatReadStatusReader.findById(roomId);
        readStatus.setLastRead(LocalDateTime.now());
    }

}
