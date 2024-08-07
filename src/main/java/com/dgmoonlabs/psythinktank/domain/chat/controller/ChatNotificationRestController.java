package com.dgmoonlabs.psythinktank.domain.chat.controller;

import com.dgmoonlabs.psythinktank.domain.chat.service.ChatNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-notification")
public class ChatNotificationRestController {
    private final ChatNotificationService chatNotificationService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return chatNotificationService.subscribe();
    }
}
