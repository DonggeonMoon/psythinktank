package com.dgmoonlabs.psythinktank.domain.content.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ContentController {
    @GetMapping("/content/{contentName}")
    public String getContent(@PathVariable String contentName) {
        return contentName;
    }
}
