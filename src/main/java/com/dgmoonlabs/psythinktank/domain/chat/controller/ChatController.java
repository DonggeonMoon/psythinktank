package com.dgmoonlabs.psythinktank.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.dgmoonlabs.psythinktank.global.constant.ViewName.CHAT;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    @GetMapping
    public String getChats() {
        return CHAT.getText();
    }
}
