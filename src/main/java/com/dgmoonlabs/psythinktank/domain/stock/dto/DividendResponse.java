package com.dgmoonlabs.psythinktank.domain.stock.dto;

import com.dgmoonlabs.psythinktank.domain.stock.model.opendart.Dividend;

public record DividendResponse(
        String symbol,
        Double value
) {
    public static DividendResponse from(Dividend dividend) {
        return new DividendResponse(
                dividend.getSymbol(),
                dividend.getValue()
        );
    }
}