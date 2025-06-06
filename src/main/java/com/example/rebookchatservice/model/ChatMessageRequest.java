package com.example.rebookchatservice.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequest {
    private String type; // ENTER, CHAT, LEAVE
    private Long roomId;
    private String sender;
    private String senderId;
    private String message;
    private LocalDateTime sendAt;
}
