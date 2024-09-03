package com.dgmoonlabs.psythinktank.domain.newsletter.controller;

import com.dgmoonlabs.psythinktank.domain.newsletter.service.NewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.NEWSLETTER_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/newsletters")
public class NewsletterRestController {
    private final NewsletterService newsletterService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getNewsletter(@PathVariable long id, Model model) {
        model.addAttribute(NEWSLETTER_KEY.getText(), newsletterService.getNewsletter(id));
        return ResponseEntity.ok()
                .body(newsletterService.downloadNewsletter(id));
    }
}
