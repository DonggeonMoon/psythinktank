package com.dgmoonlabs.psythinktank.domain.stock.dto;

import com.dgmoonlabs.psythinktank.domain.stock.model.Share;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public record ShareResponse(
        Long id,
        String symbol,
        String stockName,
        Date date,
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

    public static List<ShareResponse> from(List<Share> shares) {
        List<ShareResponse> responses = new ArrayList<>();
        String temporaryDate = "";
        for (Share share : shares) {
            if (!share.getDate().toString().equals(temporaryDate)) {
                responses.add(
                        new ShareResponse(
                                share.getId(),
                                share.getSymbol(),
                                share.getStockName(),
                                share.getDate(),
                                share.getHolderName(),
                                share.getValue()
                        )
                );
                temporaryDate = share.getDate().toString();
                continue;
            }
            responses.add(
                    new ShareResponse(
                            share.getId(),
                            share.getSymbol(),
                            share.getStockName(),
                            null,
                            share.getHolderName(),
                            share.getValue()
                    )
            );
            temporaryDate = share.getDate().toString();
        }

        return responses;
    }
}
