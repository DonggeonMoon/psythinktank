package com.dgmoonlabs.psythinktank.domain.circular.dto;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record CircularRequest(
        Long id,
        String title,
        String fileName,
        MultipartFile file,
        LocalDateTime createdAt
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
