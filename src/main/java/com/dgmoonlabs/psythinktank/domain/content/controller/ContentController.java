package com.dgmoonlabs.psythinktank.domain.content.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.dgmoonlabs.psythinktank.global.constant.ViewName.CONTENT_PREFIX;

@Controller
public class ContentController {
    @GetMapping("/contents/{contentName}")
    public String getContent(@PathVariable String contentName) {
        return CONTENT_PREFIX.getText() + contentName;
    }
}
