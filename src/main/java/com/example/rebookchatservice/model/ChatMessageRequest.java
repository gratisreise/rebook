package com.example.rebookchatservice.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequest {
    private String id; // MongoDB의 _id 필드와 매핑
    private String type; // ENTER, CHAT, LEAVE
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime sendAt;
}
