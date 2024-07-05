package com.dgmoonlabs.psythinktank.domain.member.controller;

import com.dgmoonlabs.psythinktank.domain.member.dto.MemberRequest;
import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/managerPage")
    public String getMembers(Pageable pageable, Model model) {
        model.addAttribute(MEMBERS_KEY.getText(), memberService.selectMembers(pageable));
        return MANAGER_PAGE.getText();
    }

    @GetMapping("/member")
    public String getAddMemberForm() {
        return JOIN.getText();
    }

    @PostMapping("/member")
    public String insertMember(MemberRequest memberRequest) {
        memberService.addMember(memberRequest);
        return LOGIN.redirect();
    }

    @GetMapping("/findIdAndPw")
    public String findIdAndPassword() {
        return FIND_ID_AND_PASSWORD.getText();
    }

    @GetMapping("/editMemberInfo")
    public String getModifyMemberForm(Model model, @SessionAttribute Map<String, String> member) {
        model.addAttribute(
                MEMBER_KEY.getText(),
                memberService.getMember(member.get("memberId"))
        );
        return EDIT_MEMBER_INFO.getText();
    }

    @PutMapping("/member")
    public String updateMember(MemberRequest memberRequest) {
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
