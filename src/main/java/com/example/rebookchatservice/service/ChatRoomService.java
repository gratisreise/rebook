package com.example.rebookchatservice.service;

import com.example.rebookchatservice.model.entity.ChatRoom;
import com.example.rebookchatservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void createChatRoom(String myId, String yourId) {
        chatRoomRepository.save(new ChatRoom(myId, yourId));
    }
}
