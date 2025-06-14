package com.example.rebooktradingservice.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMessage implements Serializable {
    private String message;
    private String type;
    private String tradingId;
    private String bookId;

    public NotificationMessage(Long tradingId, String content, Long bookId) {
        this.message = content;
        this.tradingId = tradingId.toString();
        this.bookId = bookId.toString();
        this.type = "TRADE";
    }

    public NotificationMessage(Long bookId, Long tradingId, String content) {
        this.message = content;
        this.tradingId = tradingId.toString();
        this.bookId = bookId.toString();
        this.type = "BOOK";
    }
}
