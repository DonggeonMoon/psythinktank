package com.dgmoonlabs.psythinktank.domain.stock.dto;

import com.dgmoonlabs.psythinktank.domain.stock.model.Dividend;

public record DividendResponse(
        String symbol,
        String stockName,
        Integer value
) {
    public static DividendResponse from(Dividend dividend) {
        return new DividendResponse(
                dividend.getSymbol(),
                dividend.getStockName(),
                dividend.getValue()
        );
    }
}