package com.dgmoonlabs.psythinktank.domain.chat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class ChatNotificationService {
    private static final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(60000L);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));

        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            log.info("SSE Connection Timeout");
        });

        emitter.onError(exception -> {
            emitters.remove(emitter);
            log.info("SSE Connection Error");
        });
        return emitter;
    }

    public void publish(String event) {
        emitters.forEach(emitter -> {
            try {
                emitter.send(
                        SseEmitter.event()
                                .data(event)
                );
            } catch (Exception exception) {
                emitters.remove(emitter);
                log.info("SSE Publish Error");
            }
        });
    }
}
