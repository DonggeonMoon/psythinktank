package com.dgmoonlabs.psythinktank.domain.newsletter.dto;

import com.dgmoonlabs.psythinktank.domain.newsletter.model.Newsletter;
import com.dgmoonlabs.psythinktank.global.constant.DateTimeFormat;

public record NewsletterResponse(
        Long id,
        String title,
        String fileName,
        String createdAt
) {
    public static NewsletterResponse from(Newsletter newsletter) {
        return new NewsletterResponse(
                newsletter.getId(),
                newsletter.getTitle(),
                newsletter.getFileName(),
                (newsletter.getCreatedAt() != null) ? newsletter.getCreatedAt().format(DateTimeFormat.DATE_TIME.getFormatter()) : null
        );
    }
}
