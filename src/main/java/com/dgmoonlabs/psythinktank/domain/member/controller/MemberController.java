package com.dgmoonlabs.psythinktank.domain.member.controller;

import com.dgmoonlabs.psythinktank.domain.member.constant.UserLevel;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberDto;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.service.LoginService;
import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final LoginService loginService;
    private final MemberService memberService;

    @GetMapping("/managerPage")
    public String getMembers(HttpSession session, @RequestParam(defaultValue = "1") int page, Model model) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (UserLevel.ADMIN.isSame(sessionInfo.getUserLevel())) {
                Page<Member> members = memberService.selectAllMember(--page);
                model.addAttribute(MEMBERS_KEY.getText(), members);

                return MANAGER_PAGE.getText();
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @GetMapping("/member")
    public String getAddMemberForm() {
        return JOIN.getText();
    }

    @PostMapping("/checkId")
    @ResponseBody
    public Map<Object, Object> checkId(@RequestBody String memberId) {
        HashMap<Object, Object> map = new HashMap<>();
        if (!loginService.checkId(memberId)) {
            map.put(IS_UNIQUE_KEY.getText(), true);
        } else {
            map.put(IS_UNIQUE_KEY.getText(), false);
        }
        return map;
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public Map<Object, Object> checkEmail(@RequestBody String memberEmail) {
        HashMap<Object, Object> map = new HashMap<>();
        if (!loginService.checkEmail(memberEmail)) {
            map.put(IS_UNIQUE_KEY2.getText(), true);
        } else {
            map.put(IS_UNIQUE_KEY2.getText(), false);
        }
        return map;
    }

    @PostMapping("/member")
    public String insertMember(HttpSession session, Member member) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            memberService.join(member);
        } else {
            session.removeAttribute(null);
        }
        return LOGIN.redirect();
    }

    @GetMapping("/findIdAndPw")
    public String findIdAndPassword() {
        return FIND_ID_AND_PASSWORD.getText();
    }

    @PostMapping("/findId")
    @ResponseBody
    public Map<String, Object> findId(@RequestBody HashMap<String, String> member) {
        Map<String, Object> map = new HashMap<>();
        Member result = memberService.selectOneMemberByEmail(member.get(MEMBER_EMAIL_KEY.getText()));
        if (result != null) {
            map.put(EXISTS_KEY.getText(), true);
            map.put(ID_KEY.getText(), result.getMemberId());
        } else {
            map.put(EXISTS_KEY.getText(), false);
            map.put(ID_KEY.getText(), null);
        }
        return map;
    }

    @PostMapping("/findPw")
    @ResponseBody
    public Map<String, Object> findPassword(@RequestBody HashMap<String, String> member) {
        Map<String, Object> map = new HashMap<>();
        Member result = memberService.selectOneMemberByEmailAndId(member.get(MEMBER_EMAIL_KEY.getText()), member.get(MEMBER_ID_KEY.getText()));
        if (result != null) {
            map.put(EXISTS_KEY.getText(), true);
            memberService.sendTempPwEmail(result.getEmail());
        } else {
            map.put(EXISTS_KEY.getText(), false);

        }
        return map;
    }

    @GetMapping("/editMemberInfo")
    public String getModifyMemberForm(HttpSession session, Model model) {
        if (session.getAttribute(SESSION_KEY.getText()) == null) {
            return LOGIN.redirect();
        }
        model.addAttribute(MEMBER_KEY.getText(), memberService.getMemberInfo(session));
        return EDIT_MEMBER_INFO.getText();
    }

    @PutMapping("/member")
    public String updateMember(HttpSession session, Member member) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(member.getMemberId())) {
                memberService.editMemberInfo(member);
                return BOARD.redirect();
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @DeleteMapping("/member")
    public String deleteMember(HttpSession session, String memberId) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (sessionInfo.getMemberId().equals(memberId)) {
                memberService.deleteMemberInfo(memberId);
                session.removeAttribute(SESSION_KEY.getText());
                return GOOD_BYE.redirect();
            } else {
                return LOGIN.redirect();
            }
        }
    }

    @GetMapping("/goodBye")
    public String getGoodByePage() {
        return GOOD_BYE.getText();
    }

    @PostMapping("/changeUserLevel")
    public String changeUserLevel(HttpSession session, Member member) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute(SESSION_KEY.getText());
        if (sessionInfo == null) {
            return LOGIN.redirect();
        } else {
            if (UserLevel.ADMIN.isSame(sessionInfo.getUserLevel())) {
                memberService.changeUserLevel(member);
                return MANAGER_PAGE.redirect();
            } else {
                return LOGIN.redirect();
            }
        }
    }
}
