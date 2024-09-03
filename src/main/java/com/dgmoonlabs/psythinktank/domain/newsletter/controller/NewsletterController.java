package com.dgmoonlabs.psythinktank.domain.newsletter.controller;

import com.dgmoonlabs.psythinktank.domain.newsletter.dto.NewsletterRequest;
import com.dgmoonlabs.psythinktank.domain.newsletter.service.NewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dgmoonlabs.psythinktank.global.constant.ApiName.NEWSLETTER;
import static com.dgmoonlabs.psythinktank.global.constant.KeyName.NEWSLETTERS_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.INSERT_NEWSLETTER;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.NEWSLETTER_LIST;

@Controller
@RequiredArgsConstructor
@RequestMapping("/newsletters")
public class NewsletterController {
    private final NewsletterService newsletterService;

    @GetMapping("/add")
    public String getCreateNewsletterForm() {
        return INSERT_NEWSLETTER.getText();
    }

    @PostMapping
    public String createNewsletter(@Valid NewsletterRequest newsletterRequest) {
        newsletterService.createNewsletter(newsletterRequest);
        return NEWSLETTER.redirect();
    }

    @GetMapping
    public String getNewsletters(Pageable pageable, Model model) {
        model.addAttribute(NEWSLETTERS_KEY.getText(), newsletterService.getNewsletters(pageable));
        return NEWSLETTER_LIST.getText();
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public String deleteNewsletter(@PathVariable long id) {
        newsletterService.deleteNewsletter(id);
        return NEWSLETTER.redirect();
    }
}
