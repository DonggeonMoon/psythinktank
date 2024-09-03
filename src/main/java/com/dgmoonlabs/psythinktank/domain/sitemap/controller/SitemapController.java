package com.dgmoonlabs.psythinktank.domain.sitemap.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dgmoonlabs.psythinktank.global.constant.ViewName.SITEMAP_MANAGEMENT;

@Controller
public class SitemapController {
    @GetMapping("/sitemap")
    @Secured("ROLE_ADMIN")
    public String getSitemapManagement() {
        return SITEMAP_MANAGEMENT.getText();
    }
}
