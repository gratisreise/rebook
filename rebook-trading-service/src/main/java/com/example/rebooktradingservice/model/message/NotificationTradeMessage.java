package com.example.rebooktradingservice.model.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationTradeMessage {
    private String message;
    private String type;
    private String tradingId;
    private String bookId;

    public NotificationTradeMessage(Long tradingId, String content, Long bookId) {
        this.message = content;
        this.tradingId = tradingId.toString();
        this.bookId = bookId.toString();
        this.type = "TRADE";
    }
}
