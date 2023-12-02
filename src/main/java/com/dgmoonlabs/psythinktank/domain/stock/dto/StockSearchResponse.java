package com.dgmoonlabs.psythinktank.domain.stock.dto;

import java.util.List;

public record StockSearchResponse(
        List<StockResponse> result
) {
    public static StockSearchResponse from(List<StockResponse> stockResponses) {
        return new StockSearchResponse(stockResponses);
    }
}
