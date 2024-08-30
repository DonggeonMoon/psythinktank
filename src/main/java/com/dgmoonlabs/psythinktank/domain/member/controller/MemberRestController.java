package com.dgmoonlabs.psythinktank.domain.member.controller;

import com.dgmoonlabs.psythinktank.domain.member.dto.*;
import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping("/check/id")
    public ResponseEntity<CheckIdResponse> checkId(@RequestBody String memberId) {
        return ResponseEntity.ok(
                memberService.checkId(memberId)
        );
    }

    @PostMapping("/check/email")
    public ResponseEntity<CheckEmailResponse> checkEmail(@RequestBody String memberEmail) {
        return ResponseEntity.ok(
                memberService.checkEmail(memberEmail)
        );
    }

    @PostMapping("/find/id")
    public ResponseEntity<FindIdResponse> findId(@Valid @RequestBody FindIdRequest request) {
        return ResponseEntity.ok(
                memberService.findId(request.memberEmail())
        );
    }

    @PostMapping("/find/password")
    public ResponseEntity<FindPasswordResponse> findPassword(@Valid @RequestBody FindPasswordRequest request) {
        return ResponseEntity.ok(
                memberService.findPassword(request)
        );
    }
}
