package com.example.rebooknotificationservice.model.message;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationChatMessage extends NotificationMessage {
    private String userId;
    private String roomId;
}
