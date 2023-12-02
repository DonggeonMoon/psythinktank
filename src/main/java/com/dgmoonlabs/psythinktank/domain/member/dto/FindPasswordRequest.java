package com.dgmoonlabs.psythinktank.domain.member.dto;

public record FindPasswordRequest(
        String memberEmail,
        String memberId
) {
}
