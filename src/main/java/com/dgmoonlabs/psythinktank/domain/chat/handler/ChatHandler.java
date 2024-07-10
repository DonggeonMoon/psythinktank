package com.dgmoonlabs.psythinktank.domain.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatHandler extends TextWebSocketHandler {
    private static final List<WebSocketSession> sessions = new ArrayList<>();
    private static final FixedSizeQueue<String> messages = new FixedSizeQueue<>(50);

    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws IOException {
        log.info("Connection established: {}", session.getId());
        sessions.add(session);
        String json = objectMapper.writeValueAsString(messages.toStringArray());
        session.sendMessage(new TextMessage(json));
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message) {
        messages.add(message.getPayload().toString());

        sessions.forEach(openSession -> {
            try {
                String json = objectMapper.writeValueAsString(messages.toStringArray());
                openSession.sendMessage(new TextMessage(json));
            } catch (IOException e) {
                log.error("error: {}", e.getMessage());
            }
        });
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus closeStatus) {
        log.info("Connection closed: {}", session.getId());
        sessions.remove(session);
    }

    @RequiredArgsConstructor
    static class FixedSizeQueue<T> {
        private final int maxSize;
        private final Queue<T> queue = new ConcurrentLinkedQueue<>();

        public void add(final T element) {
            queue.add(element);
        }

        public void push(final T element) {
            if (queue.size() >= maxSize) {
                queue.poll();
            }
            queue.add(element);
        }

        public int size() {
            return queue.size();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        @Override
        public String toString() {
            return Arrays.toString(queue.toArray());
        }

        public String[] toStringArray() {
            String[] array = new String[messages.queue.size()];
            return messages.queue.toArray(array);
        }
    }
}
