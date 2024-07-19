package com.dgmoonlabs.psythinktank.domain.member.controller;

import com.dgmoonlabs.psythinktank.domain.member.dto.MemberRequest;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberUserLevelRequest;
import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import com.dgmoonlabs.psythinktank.global.constant.ViewName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

import static com.dgmoonlabs.psythinktank.global.constant.ApiName.LOGIN;
import static com.dgmoonlabs.psythinktank.global.constant.ApiName.MEMBERS;
import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public String getMembers(Pageable pageable, Model model) {
        model.addAttribute(MEMBERS_KEY.getText(), memberService.selectMembers(pageable));
        return MANAGER_PAGE.getText();
    }

    @GetMapping("/add")
    public String getAddMemberForm() {
        return JOIN.getText();
    }

    @PostMapping
    public String insertMember(@Valid MemberRequest memberRequest) {
        memberService.addMember(memberRequest);
        return ViewName.LOGIN.redirect();
    }

    @GetMapping("/modify")
    public String getModifyMemberForm(Model model, @SessionAttribute Map<String, String> member) {
        model.addAttribute(
                MEMBER_KEY.getText(),
                memberService.getMember(member.get("memberId"))
        );
        return EDIT_MEMBER_INFO.getText();
    }

    @PutMapping
    public String updateMember(@Valid MemberRequest memberRequest) {
        memberService.editMember(memberRequest);
        return LOGIN.redirect();
    }

    @DeleteMapping
    public String deleteMember(String memberId, HttpSession session) {
        memberService.deleteMember(memberId);
        session.removeAttribute(SESSION_KEY.getText());
        return GOOD_BYE.redirect();
    }

    @GetMapping("/findIdAndPw")
    public String findIdAndPassword() {
        return FIND_ID_AND_PASSWORD.getText();
    }

    @PutMapping("/change/userLevel")
    public String changeUserLevel(@Valid MemberUserLevelRequest memberRequest) {
        memberService.changeUserLevel(memberRequest);
        return MEMBERS.redirect();
    }

    @GetMapping("/goodBye")
    public String getGoodByePage() {
        return GOOD_BYE.getText();
    }
}
