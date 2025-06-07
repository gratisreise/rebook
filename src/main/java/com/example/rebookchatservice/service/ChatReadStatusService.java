package com.example.rebookchatservice.service;

import com.example.rebookchatservice.model.entity.ChatMessage;
import com.example.rebookchatservice.model.entity.ChatReadStatus;
import com.example.rebookchatservice.model.entity.ChatRoom;
import com.example.rebookchatservice.model.entity.compositekey.ChatReadStatusId;
import com.example.rebookchatservice.repository.ChatReadStatusRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatReadStatusService {

    private final ChatReadStatusReader chatReadStatusReader;
    private final ChatMessageReader chatMessageReader;
    private final ChatReadStatusRepository chatReadStatusRepository;

    @Transactional
    public void patchLastRead(Long roomId, String userId) {
        ChatReadStatusId statusId = new ChatReadStatusId(roomId, userId);
        ChatReadStatus readStatus = chatReadStatusReader.findById(statusId);
        LocalDateTime lastRead = chatMessageReader.lastMessageTime(roomId);
        readStatus.setLastRead(lastRead);
    }


    @Transactional
    public void crateChatReadStatus(String myId, String yourId, Long roomId) {
        ChatRoom room = new ChatRoom(roomId);
        ChatReadStatusId statusId1 = new ChatReadStatusId(roomId, myId);
        ChatReadStatusId statusId2 = new ChatReadStatusId(roomId, yourId);
        ChatReadStatus readStatus1 = new ChatReadStatus(statusId1, room);
        ChatReadStatus readStatus2 = new ChatReadStatus(statusId2, room);
        chatReadStatusRepository.save(readStatus1);
        chatReadStatusRepository.save(readStatus2);
    }
}
