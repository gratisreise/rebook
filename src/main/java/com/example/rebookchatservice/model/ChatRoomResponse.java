package com.example.rebookchatservice.model;

import com.example.rebookchatservice.model.entity.ChatRoom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomResponse {
    private Long id;
    private String user1Id;
    private String user2Id;
    private LocalDateTime createdAt;
    private Long unreadCount;

    public ChatRoomResponse(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.user1Id = chatRoom.getUser1Id();
        this.user2Id = chatRoom.getUser2Id();
        this.createdAt = chatRoom.getCreatedAt();
    }
}
