package com.example.rebooknotificationservice.service;

import com.example.rebooknotificationservice.feigns.UserClient;
import com.example.rebooknotificationservice.model.message.NotificationBookMessage;
import com.example.rebooknotificationservice.model.message.NotificationChatMessage;
import com.example.rebooknotificationservice.model.message.NotificationMessage;
import com.example.rebooknotificationservice.model.message.NotificationTradeMessage;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@RequiredArgsConstructor
public class SseService {

    // 클라이언트별 Emitter 관리
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final NotificationService notificationService;
    private final UserClient userClient;

    // 알림 메시지 RabbitMq에서 수신
    @RabbitListener(queues = "book.notification.queue")
    public void receiveBookNotification(@Valid NotificationBookMessage message) {

        List<String> userIds = userClient.findUserIdsByCategory(message.getCategory());
        userIds.forEach(userId -> sendNotification(message, userId));
    }

    @RabbitListener(queues = "trade.notification.queue")
    public void receiveTradeNotification(@Valid NotificationTradeMessage message) {
        List<String> userIds = userClient.findUserIdsByMarkedBook(message.getBookId());
        userIds.forEach(userId ->sendNotification(message, userId));
    }

    @RabbitListener(queues = "chat.notification.queue")
    public void receiveChatNotification(@Valid NotificationChatMessage message) {
        notificationService.createChatNotification(message);
        sendNotification(message, message.getUserId());
    }

    private void sendNotification(NotificationMessage message, String userId) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification")
                    .data(message.getMessage()));
            } catch (Exception e) {
                emitters.remove(userId);
            }
        }
    }

    public SseEmitter connect(String userId) {
        SseEmitter emitter = new SseEmitter();

        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError(e -> emitters.remove(userId));

        // 연결 직후 테스트 메시지 전송
        try {
            emitter.send(SseEmitter.event()
                .name("connected")
                .data("connected"));
        } catch (IOException e) {
            emitters.remove(userId);
        }

        return emitter;
    }
}
