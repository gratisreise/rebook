package com.example.rebookchatservice.service;

import com.example.rebookchatservice.model.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessagingTemplate messagingTemplate;

    public void enterEvent(ChatMessage message) {
        // (선택) 인원수 증가, 참여자 등록 등 부가 로직
        message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        message.setType("ENTER");

        // 해당 채팅방을 구독 중인 모든 클라이언트에게 입장 알림 브로드캐스트
        messagingTemplate.convertAndSend(
            "/topic/room/" + message.getRoomId(),
            message
        );
    }
}
