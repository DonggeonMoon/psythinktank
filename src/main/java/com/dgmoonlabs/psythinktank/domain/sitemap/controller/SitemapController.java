package com.dgmoonlabs.psythinktank.domain.sitemap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dgmoonlabs.psythinktank.global.constant.ViewName.SITEMAP_MANAGEMENT;

@Controller
public class SitemapController {
    @GetMapping("/sitemap")
    public String getSitemapManagement() {
        return SITEMAP_MANAGEMENT.getText();
    }
}
