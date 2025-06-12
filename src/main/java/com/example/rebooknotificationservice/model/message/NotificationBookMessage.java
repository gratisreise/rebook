package com.example.rebooknotificationservice.model.message;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationBookMessage extends NotificationMessage {
    private String bookId;
    private String category;
}
