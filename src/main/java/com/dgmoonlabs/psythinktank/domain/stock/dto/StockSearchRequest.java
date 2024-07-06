package com.dgmoonlabs.psythinktank.domain.stock.dto;

import javax.validation.constraints.NotBlank;

public record StockSearchRequest(
        @NotBlank(message = "검색어를 입력해주세요.") String searchText
) {
}
