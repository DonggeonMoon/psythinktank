package com.dgmoonlabs.psythinktank.domain.member.dto;

public record LoginResponse(
        boolean isSucceeded,
        int error,
        Integer loginTryCount
) {
}
