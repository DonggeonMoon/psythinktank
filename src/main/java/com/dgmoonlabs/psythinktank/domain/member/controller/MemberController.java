package com.dgmoonlabs.psythinktank.domain.member.controller;

import com.dgmoonlabs.psythinktank.domain.member.dto.*;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.MEMBERS_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.KeyName.SESSION_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/managerPage")
    public String getMembers(Pageable pageable, HttpSession session, Model model) {
        Page<Member> members = memberService.selectMembers(pageable);
        model.addAttribute(MEMBERS_KEY.getText(), members);
        return MANAGER_PAGE.getText();
    }

    @GetMapping("/member")
    public String getAddMemberForm() {
        return JOIN.getText();
    }

    @PostMapping("/checkId")
    @ResponseBody
    public ResponseEntity<CheckIdResponse> checkId(@RequestBody String memberId) {
        return ResponseEntity.ok(memberService.checkId(memberId));
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public ResponseEntity<CheckEmailResponse> checkEmail(@RequestBody String memberEmail) {
        return ResponseEntity.ok(memberService.checkEmail(memberEmail));
    }

    @PostMapping("/member")
    public String insertMember(MemberRequest memberRequest, HttpSession session) {
        memberService.addMember(memberRequest);
        return LOGIN.redirect();
    }

    @GetMapping("/findIdAndPw")
    public String findIdAndPassword() {
        return FIND_ID_AND_PASSWORD.getText();
    }

    @PostMapping("/findId")
    @ResponseBody
    public ResponseEntity<FindIdResponse> findId(@RequestBody FindIdRequest request) {
        return ResponseEntity.ok(memberService.selectMemberByEmail(request.memberEmail()));
    }

    @PostMapping("/findPw")
    @ResponseBody
    public ResponseEntity<FindPasswordResponse> findPassword(@RequestBody FindPasswordRequest request) {
        return ResponseEntity.ok(memberService.selectMemberByEmailAndMemberId(request));
    }

    @GetMapping("/editMemberInfo")
    public String getModifyMemberForm(HttpSession session, Model model) {
        return EDIT_MEMBER_INFO.getText();
    }

    @PutMapping("/member")
    public String updateMember(MemberRequest memberRequest, HttpSession session) {
        memberService.editMember(memberRequest);
        return BOARD_LIST.redirect();
    }

    @DeleteMapping("/member")
    public String deleteMember(String memberId, HttpSession session) {
        memberService.deleteMember(memberId);
        session.removeAttribute(SESSION_KEY.getText());
        return GOOD_BYE.redirect();
    }

    @GetMapping("/goodBye")
    public String getGoodByePage() {
        return GOOD_BYE.getText();
    }

    @PostMapping("/changeUserLevel")
    public String changeUserLevel(MemberRequest memberRequest) {
        memberService.changeUserLevel(memberRequest);
        return MANAGER_PAGE.redirect();
    }
}
