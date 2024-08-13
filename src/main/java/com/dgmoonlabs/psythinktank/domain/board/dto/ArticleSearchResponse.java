package com.dgmoonlabs.psythinktank.domain.board.dto;

import java.util.List;

public record ArticleSearchResponse(
        List<ArticleResponse> result
) {
}
