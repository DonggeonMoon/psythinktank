package com.dgmoonlabs.psythinktank.domain.member.controller;

import com.dgmoonlabs.psythinktank.domain.member.dto.MemberRequest;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberUserLevelRequest;
import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import com.dgmoonlabs.psythinktank.global.constant.ViewName;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

import static com.dgmoonlabs.psythinktank.global.constant.ApiName.GOOD_BYE;
import static com.dgmoonlabs.psythinktank.global.constant.ApiName.LOGIN;
import static com.dgmoonlabs.psythinktank.global.constant.ApiName.*;
import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/add")
    public String getCreateMemberForm() {
        return JOIN.getText();
    }

    @PostMapping
    public String createMember(@Valid MemberRequest memberRequest) {
        memberService.createMember(memberRequest);
        return ViewName.LOGIN.redirect();
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public String getMembers(Pageable pageable, Model model) {
        model.addAttribute(MEMBERS_KEY.getText(), memberService.getMembers(pageable));
        return MANAGER_PAGE.getText();
    }

    @GetMapping("/modify")
    public String getUpdateMemberForm(Model model, @SessionAttribute Map<String, String> member) {
        model.addAttribute(MEMBER_KEY.getText(), memberService.getMember(member.get("memberId")));
        return EDIT_MEMBER_INFO.getText();
    }

    @PutMapping
    public String updateMember(@Valid MemberRequest memberRequest) {
        memberService.updateMember(memberRequest);
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
    @Secured("ROLE_ADMIN")
    public String changeUserLevel(@Valid MemberUserLevelRequest memberRequest) {
        memberService.changeUserLevel(memberRequest);
        return MEMBERS.redirect();
    }

    @GetMapping("/goodBye")
    public String getGoodByePage() {
        return ViewName.GOOD_BYE.getText();
    }
}
