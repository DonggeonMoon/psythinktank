package com.dgmoonlabs.psythinktank.domain.stock.dto;

import java.util.List;

public record ShareSearchResponse(
        List<ShareResponse> result
) {
    public static ShareSearchResponse from(List<ShareResponse> responses) {
        return new ShareSearchResponse(responses);
    }
}
