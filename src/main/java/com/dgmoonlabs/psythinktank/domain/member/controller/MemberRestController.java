package com.dgmoonlabs.psythinktank.domain.member.controller;

import com.dgmoonlabs.psythinktank.domain.member.dto.*;
import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberRestController {
    private final MemberService memberService;

    @PostMapping("/checkId")
    public ResponseEntity<CheckIdResponse> checkId(@RequestBody String memberId) {
        return ResponseEntity.ok(memberService.checkId(memberId));
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<CheckEmailResponse> checkEmail(@RequestBody String memberEmail) {
        return ResponseEntity.ok(memberService.checkEmail(memberEmail));
    }

    @PostMapping("/findId")
    public ResponseEntity<FindIdResponse> findId(@RequestBody FindIdRequest request) {
        return ResponseEntity.ok(memberService.selectMemberByEmail(request.memberEmail()));
    }

    @PostMapping("/findPw")
    public ResponseEntity<FindPasswordResponse> findPassword(@RequestBody FindPasswordRequest request) {
        return ResponseEntity.ok(memberService.selectMemberByEmailAndMemberId(request));
    }
}
