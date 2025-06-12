package com.example.rebooknotificationservice.model.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationTradeMessage extends NotificationMessage {
    String tradingId;
    String bookId;
}
