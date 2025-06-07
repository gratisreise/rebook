package com.example.rebookchatservice.service;

import com.example.rebookchatservice.common.PageResponse;
import com.example.rebookchatservice.exception.CDuplicatedDataException;
import com.example.rebookchatservice.model.ChatRoomResponse;
import com.example.rebookchatservice.model.entity.ChatReadStatus;
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
    private final ChatReadStatusService chatReadStatusService;

    @Transactional
    public Long createChatRoom(String myId, String yourId) {
        if(isRoomExists(myId, yourId)){
           throw new CDuplicatedDataException("이미 채팅방이 존재합니다.");
        }

        ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom(myId, yourId));
        chatReadStatusService.crateChatReadStatus(myId, yourId, chatRoom.getId());
        return chatRoom.getId();
    }

    private boolean isRoomExists(String myId, String yourId) {
        if(myId.compareTo(yourId) < 0){
            return chatRoomRepository.existsByUser1IdAndUser2Id(myId, yourId);
        } else {
            return  chatRoomRepository.existsByUser1IdAndUser2Id(yourId, myId);
        }
    }


    public PageResponse<ChatRoomResponse> getMyChatRooms(String myId, Pageable pageable) {
        Page<ChatRoom> rooms = chatRoomReader.getChatRooms(myId, pageable);
        Page<ChatRoomResponse> roomResponses = rooms.map(ChatRoomResponse::new);
        return new PageResponse<>(roomResponses);
    }

}
