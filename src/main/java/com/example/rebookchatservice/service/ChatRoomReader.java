package com.example.rebookchatservice.service;

import com.example.rebookchatservice.model.entity.ChatRoom;
import com.example.rebookchatservice.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ChatRoomReader {
    private final ChatRoomRepository chatRoomRepository;

    public Page<ChatRoom> getChatRooms(String myId, Pageable pageable) {
        return chatRoomRepository.findByUser1IdOrUser2Id(myId, myId, pageable);
    }
}
