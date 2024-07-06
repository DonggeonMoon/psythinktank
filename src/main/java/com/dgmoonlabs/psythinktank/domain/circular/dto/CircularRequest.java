package com.dgmoonlabs.psythinktank.domain.circular.dto;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CircularRequest(
        Long id,
        @NotBlank(message = "제목을 입력해주세요.") String title,
        @NotBlank(message = "파일 이름이 누락되었습니다.") String fileName,
        @NotNull(message = "파일이 누락되었습니다.") MultipartFile file
) {
    public Circular toEntity() {
        return Circular.builder()
                .id(id)
                .title(title)
                .fileName(fileName)
                .build();
    }
}
