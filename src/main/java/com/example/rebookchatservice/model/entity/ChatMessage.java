package com.example.rebookchatservice.model.entity;


import jakarta.persistence.Id;
import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chatting")
public class ChatMessage {
    @Id
    private String id;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime sendAt;
}
