package com.example.rebooknotificationservice.model.message;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationTradeMessage extends NotificationMessage {
    @NotBlank
    String tradingId;

    @NotBlank
    String bookId;
}
