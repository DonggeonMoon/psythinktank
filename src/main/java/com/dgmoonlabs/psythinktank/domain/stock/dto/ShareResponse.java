package com.dgmoonlabs.psythinktank.domain.stock.dto;

import com.dgmoonlabs.psythinktank.domain.stock.model.Share;

import java.time.LocalDate;

public record ShareResponse(
        Long id,
        String symbol,
        String stockName,
        LocalDate date,
        String holderName,
        Double value
) {
    public static ShareResponse from(Share share) {
        return new ShareResponse(
                share.getId(),
                share.getSymbol(),
                share.getStockName(),
                share.getDate(),
                share.getHolderName(),
                share.getValue()
        );
    }
}
