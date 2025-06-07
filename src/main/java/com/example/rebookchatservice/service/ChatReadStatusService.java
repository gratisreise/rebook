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
        ChatReadStatus readStatus1 = generateChatReadStatus(myId, roomId);
        ChatReadStatus readStatus2 = generateChatReadStatus(yourId, roomId);
        chatReadStatusRepository.save(readStatus1);
        chatReadStatusRepository.save(readStatus2);
    }

    public int getCount(String myId, Long id) {

        return 0;
    }

    public LocalDateTime getLastRead(String myId, Long roomId) {
        ChatReadStatusId statusId = new ChatReadStatusId(roomId, myId);
        ChatReadStatus readStatus = chatReadStatusReader.findById(statusId);
        return readStatus.getLastRead();
    }

    private ChatReadStatus generateChatReadStatus(String userId, Long roomId){
        ChatRoom room = new ChatRoom(roomId);
        ChatReadStatusId statusId = new ChatReadStatusId(roomId, userId);
        return new ChatReadStatus(statusId, room);
    }
}
