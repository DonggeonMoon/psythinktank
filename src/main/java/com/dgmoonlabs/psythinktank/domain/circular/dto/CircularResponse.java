package com.dgmoonlabs.psythinktank.domain.circular.dto;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;

import java.sql.Timestamp;

public record CircularResponse(
        Long id,
        String title,
        String fileName,
        Timestamp createdAt
) {
    public static CircularResponse from(Circular circular) {
        return new CircularResponse(
                circular.getId(),
                circular.getTitle(),
                circular.getFileName(),
                circular.getCreatedAt()
        );
    }
}
