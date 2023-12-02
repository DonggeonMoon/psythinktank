package com.dgmoonlabs.psythinktank.domain.member.dto;

public record LoginRequest(
        String memberId,
        String password
) {
}
