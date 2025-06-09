package com.example.rebooknotificationservice.service;

import com.example.rebooknotificationservice.model.NotificationRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class SseService {

    // 클라이언트별 Emitter 관리
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final NotificationService notificationService;

    // 알림 메시지 RabbitMQ에서 수신
    @RabbitListener(queues = "${notification.queue}")
    public void receiveNotification(NotificationRequest message) {
        // 1. 알림 DB에 저장 (생략 가능)
        notificationService.createNotification(message);

        // 2. SSE 연결된 클라이언트에게 실시간 전송
        SseEmitter emitter = emitters.get(message.getUserId());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("notification")
                    .data(message.getContent()));
            } catch (Exception e) {
                emitters.remove(message.getUserId());
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
