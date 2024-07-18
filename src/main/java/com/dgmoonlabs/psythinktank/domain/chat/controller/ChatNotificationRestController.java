package com.dgmoonlabs.psythinktank.domain.chat.controller;

import com.dgmoonlabs.psythinktank.domain.chat.service.ChatNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-notification")
@Slf4j
public class ChatNotificationRestController {
    private final ChatNotificationService chatNotificationService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        return chatNotificationService.subscribe();
    }
}
