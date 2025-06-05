package com.example.rebookchatservice.service;

import com.example.rebookchatservice.common.PageResponse;
import com.example.rebookchatservice.model.ChatRoomResponse;
import com.example.rebookchatservice.model.entity.ChatRoom;
import com.example.rebookchatservice.repository.ChatRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomReader chatRoomReader;

    @Transactional
    public void createChatRoom(String myId, String yourId) {
        chatRoomRepository.save(new ChatRoom(myId, yourId));
    }


    public PageResponse<ChatRoomResponse> getMyChatRooms(String myId, Pageable pageable) {
        Page<ChatRoom> rooms = chatRoomReader.getChatRooms(myId, pageable);
        Page<ChatRoomResponse> roomResponses = rooms.map(ChatRoomResponse::new);
        return new PageResponse<>(roomResponses);
    }


}
