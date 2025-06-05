package com.example.rebookchatservice.controller;

import com.example.rebookchatservice.model.ChatMessageRequest;
import com.example.rebookchatservice.model.entity.ChatMessage;
import com.example.rebookchatservice.service.ChatMessageService;
import com.example.rebookchatservice.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatMessageController {
    // @RquestParam
    // @RequestHeader("X-User-Id) String myId

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    //채팅방 입장메세지
    @MessageMapping("/enter")
    public void enterChat(ChatMessageRequest chatMessage) {
        chatMessageService.enterEvent(chatMessage);
    }

    // 메시지 수신 및 저장, 브로드캐스트
    @MessageMapping("/message")
    public void receiveMessage(ChatMessageRequest request) {
        chatMessageService.receiveMessage(request);

    }


}
