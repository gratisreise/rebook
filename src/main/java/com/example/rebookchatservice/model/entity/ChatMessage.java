package com.example.rebookchatservice.model.entity;


import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chatting")
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

    @Id
    private String id; // MongoDB의 _id 필드와 매핑
    private String type; // ENTER, CHAT, LEAVE
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime sendAt;
}
