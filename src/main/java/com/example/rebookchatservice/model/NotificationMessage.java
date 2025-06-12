package com.example.rebookchatservice.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMessage implements Serializable {
    private String message;
    private String type;
    private String userId;
    private String roomId;

    public NotificationMessage(ChatMessageRequest request, String message) {
        this.message = message;
        this.type = "CHAT";
        this.userId = request.getReceiverId();
        this.roomId = request.getRoomId().toString();
    }
}
