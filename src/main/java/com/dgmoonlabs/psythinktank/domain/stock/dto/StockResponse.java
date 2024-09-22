package com.dgmoonlabs.psythinktank.domain.stock.dto;

import com.dgmoonlabs.psythinktank.domain.stock.model.StockInfo;

public record StockResponse(
        Long id,
        String symbol,
        String name,
        String corporationClass,
        String corporationCode,
        String overview
) {
    public static StockResponse from(StockInfo stockInfo) {
        return new StockResponse(
                stockInfo.getId(),
                stockInfo.getSymbol(),
                stockInfo.getName(),
                stockInfo.getCorporationClass(),
                stockInfo.getCorporationCode(),
                (stockInfo.getOverview() != null) ? stockInfo.getOverview() : ""
        );
    }
}
