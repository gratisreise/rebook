package com.example.rebookchatservice.service;

import com.example.rebookchatservice.model.ChatMessageRequest;
import com.example.rebookchatservice.model.entity.ChatMessage;
import com.example.rebookchatservice.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;

    public void enterEvent(ChatMessageRequest request) {
        // 인원수 증가, 참여자 등록 등 부가 로직
        request.setMessage(request.getSender() + "님이 입장하셨습니다.");
        request.setType("ENTER");

        // 해당 채팅방을 구독 중인 모든 클라이언트에게 입장 알림 브로드캐스트
        String destination = "/topic/room/" + request.getRoomId();
        messagingTemplate.convertAndSend(destination, request);
    }

    public void receiveMessage(ChatMessageRequest request) {
        // 1. 메시지 저장 (DB, MongoDB 등)
        saveMessage(request);

        // 2. 해당 채팅방 구독자들에게 메시지 전송
        String destination = "/sub/chatroom/" + request.getId();
        messagingTemplate.convertAndSend(destination, request);
    }

    private void saveMessage(ChatMessageRequest request) {
        ChatMessage chatMessage = new ChatMessage(request);
        chatMessageRepository.save(chatMessage);
    }
}
