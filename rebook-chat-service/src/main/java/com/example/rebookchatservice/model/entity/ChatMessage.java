package com.example.rebookchatservice.model.entity;


import com.example.rebookchatservice.model.ChatMessageRequest;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chatting")
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

    @Id
    private String id; // MongoDB의 _id 필드와 매핑
    private String type; // ENTER, CHAT, LEAVE
    private Long roomId;
    private String senderId;
    private String message;
    @CreatedDate
    private LocalDateTime sendAt;

    public ChatMessage(ChatMessageRequest request) {
        this.type = request.getType();
        this.roomId = request.getRoomId();
        this.senderId = request.getSenderId();
        this.message = request.getMessage();
    }
}
