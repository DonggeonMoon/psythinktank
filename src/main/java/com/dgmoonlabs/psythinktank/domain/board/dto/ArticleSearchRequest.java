package com.dgmoonlabs.psythinktank.domain.board.dto;

import javax.validation.constraints.NotBlank;

public record ArticleSearchRequest(
        @NotBlank(message = "검색어를 입력해주세요.") String searchText
) {
}
