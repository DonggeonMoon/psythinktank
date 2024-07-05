package com.dgmoonlabs.psythinktank.domain.circular.dto;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import com.dgmoonlabs.psythinktank.global.constant.DateTimeFormat;

public record CircularResponse(
        Long id,
        String title,
        String fileName,
        String createdAt
) {
    public static CircularResponse from(Circular circular) {
        return new CircularResponse(
                circular.getId(),
                circular.getTitle(),
                circular.getFileName(),
                (circular.getCreatedAt() != null) ? circular.getCreatedAt().format(DateTimeFormat.DATE_TIME.getFormatter()) : null
        );
    }
}
