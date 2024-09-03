package com.dgmoonlabs.psythinktank.domain.newsletter.dto;

import com.dgmoonlabs.psythinktank.domain.newsletter.model.Newsletter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record NewsletterRequest(
        Long id,
        @NotBlank(message = "제목을 입력해주세요.") String title,
        @NotNull(message = "파일이 누락되었습니다.") MultipartFile file
) {
    public Newsletter toEntity() {
        return Newsletter.builder()
                .id(id)
                .title(title)
                .fileName(file.getOriginalFilename())
                .build();
    }
}
