package com.example.rebookchatservice.model;

import com.example.rebookchatservice.model.entity.ChatMessage;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageResponse {
    private String type; // ENTER, CHAT, LEAVE
    private Long roomId;
    private String senderId;
    private String message;
    private LocalDateTime sendAt;

    public ChatMessageResponse(ChatMessage chatMessage) {
        this.type = chatMessage.getType();
        this.roomId = chatMessage.getRoomId();
        this.senderId = chatMessage.getSenderId();
        this.message = chatMessage.getMessage();
        this.sendAt = chatMessage.getSendAt();
    }
}
