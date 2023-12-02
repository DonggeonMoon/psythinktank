package com.dgmoonlabs.psythinktank.domain.board.dto;

import java.util.List;

public record BoardSearchResponse(
        List<BoardResponse> result
) {
}
