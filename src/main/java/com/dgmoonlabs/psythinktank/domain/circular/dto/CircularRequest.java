package com.dgmoonlabs.psythinktank.domain.circular.dto;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;

import java.sql.Timestamp;

public record CircularRequest(
        Long id,
        String title,
        String fileName,
        Timestamp createdAt
) {
    public Circular toEntity() {
        return Circular.builder()
                .id(id)
                .title(title)
                .fileName(fileName)
                .createdAt(createdAt)
                .build();
    }
}
