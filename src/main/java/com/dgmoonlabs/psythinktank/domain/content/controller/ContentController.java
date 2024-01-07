package com.dgmoonlabs.psythinktank.domain.content.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ContentController {
    @GetMapping("/content/{name}")
    public String getContent(@PathVariable String name) {
        return name;
    }
}
