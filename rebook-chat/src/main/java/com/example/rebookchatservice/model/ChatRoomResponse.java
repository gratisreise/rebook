package com.example.rebookchatservice.model;

import com.example.rebookchatservice.model.entity.ChatRoom;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomResponse {
    private Long id;
    private String myId;
    private String yourId;
    private LocalDateTime createdAt;
    private Long unreadCount;

    public ChatRoomResponse(ChatRoom chatRoom, String myId) {
        this.id = chatRoom.getId();
        this.myId = chatRoom.getUser1Id();
        this.yourId = myId.equals(chatRoom.getUser1Id())
            ? chatRoom.getUser2Id() : chatRoom.getUser1Id();
        this.createdAt = chatRoom.getCreatedAt();
    }
}
