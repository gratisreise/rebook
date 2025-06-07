package com.example.rebookchatservice.service;

import com.example.rebookchatservice.common.PageResponse;
import com.example.rebookchatservice.model.ChatMessageRequest;
import com.example.rebookchatservice.model.ChatMessageResponse;
import com.example.rebookchatservice.model.entity.ChatMessage;
import com.example.rebookchatservice.repository.ChatMessageRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatReadStatusService chatReadStatusService;

    public void enterEvent(ChatMessageRequest request) {
        // 인원수 증가, 참여자 등록 등 부가 로직
        request.setMessage(request.getSender() + "님이 입장하셨습니다.");
        request.setType("ENTER");

        chatReadStatusService.patchLastRead(request.getRoomId(), request.getSenderId());

        // 해당 채팅방을 구독 중인 모든 클라이언트에게 입장 알림 브로드캐스트
        String destination = "/topic/room/" + request.getRoomId();
        log.info("destination: {}", destination);
        messagingTemplate.convertAndSend(destination, request);
    }

    @Transactional
    public void receiveMessage(ChatMessageRequest request) {
        log.info("request received: {}", request);
        // 1. 메시지 저장 (DB, MongoDB 등)
        saveMessage(request);


        // 2. 해당 채팅방 구독자들에게 메시지 전송
        String destination = "/topic/room/" + request.getRoomId();
        log.info("destination: {}", destination);
        messagingTemplate.convertAndSend(destination, request);
    }


    private void saveMessage(ChatMessageRequest request) {
        ChatMessage chatMessage = new ChatMessage(request);
        chatMessageRepository.save(chatMessage);
    }

    public void leaveMessage(ChatMessageRequest request) {
        request.setMessage(request.getSender() + "님이 퇴장했습니다.");
        request.setType("LEAVE");

        //
        chatReadStatusService.patchLastRead(request.getRoomId(), request.getSenderId());

        messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), request);
    }


    public PageResponse<ChatMessageResponse> getRecentMessage(Long roomId, Pageable pageable) {
        Page<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId, pageable);
        Page<ChatMessageResponse> responses = messages.map(ChatMessageResponse::new);
        return new PageResponse<>(responses);
    }

    public long getUnreadCount(String myId, Long roomId) {
        //마지막 읽은 날짜 확인
        LocalDateTime lastRead = chatReadStatusService.getLastRead(myId, roomId);
        //해당 날짜 이후 메세지 숫자 반환
        return chatMessageRepository.countByRoomIdAndSendAtAfter(roomId, lastRead);
    }
}
