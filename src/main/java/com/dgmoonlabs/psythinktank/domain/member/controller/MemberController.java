package com.dgmoonlabs.psythinktank.domain.member.controller;

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

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final LoginService loginService;
    private final MemberService memberService;

    @GetMapping("/member")
    public String insertMemberInfo() {
        return "join";
    }

    @PostMapping("/checkId")
    @ResponseBody
    public Map<Object, Object> checkId(@RequestBody String memberId) {
        HashMap<Object, Object> map = new HashMap<>();
        if (!loginService.checkId(memberId)) {
            map.put("isUnique", true);
        } else {
            map.put("isUnique", false);
        }
        return map;
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public Map<Object, Object> checkEmail(@RequestBody String memberEmail) {
        HashMap<Object, Object> map = new HashMap<>();
        if (!loginService.checkEmail(memberEmail)) {
            map.put("isUnique2", true);
        } else {
            map.put("isUnique2", false);
        }
        return map;
    }

    @PostMapping("/member")
    public String insertMemberInfo2(HttpSession session, Member member) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            memberService.join(member);
        } else {
            session.removeAttribute(null);
        }
        return "redirect:/login";
    }

    @GetMapping("/findIdAndPw")
    public String findIdAndPw() {
        return "findIdAndPw";
    }

    @PostMapping("/findId")
    @ResponseBody
    public Map<String, Object> findId(@RequestBody HashMap<String, String> member) {
        Map<String, Object> map = new HashMap<>();
        Member result = memberService.selectOneMemberByEmail(member.get("memberEmail"));
        if (result != null) {
            map.put("exists", true);
            map.put("id", result.getMemberId());
        } else {
            map.put("exists", false);
            map.put("id", null);
        }
        return map;
    }

    @PostMapping("/findPw")
    @ResponseBody
    public Map<String, Object> findPw(@RequestBody HashMap<String, String> member) {
        Map<String, Object> map = new HashMap<>();
        Member result = memberService.selectOneMemberByEmailAndId(member.get("memberEmail"), member.get("memberId"));
        if (result != null) {
            map.put("exists", true);
            memberService.sendTempPwEmail(result.getMemberEmail());
        } else {
            map.put("exists", false);

        }
        return map;
    }

    @GetMapping("/editMemberInfo")
    public String editMemberInfo(HttpSession session, Model model) {
        if (session.getAttribute("member") == null) {
            return "redirect:/login";
        }
        model.addAttribute("memberInfo", memberService.getMemberInfo(session));
        return "editMemberInfo";
    }

    @PutMapping("/member")
    public String editMemberInfo2(HttpSession session, Member member) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(member.getMemberId())) {
                memberService.editMemberInfo(member);
                return "redirect:/boardList";
            } else {
                return "redirect:/login";
            }
        }
    }

    @DeleteMapping("/member")
    public String deleteMemberInfo(HttpSession session, String memberId) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getMemberId().equals(memberId)) {
                memberService.deleteMemberInfo(memberId);
                session.removeAttribute("member");
                return "redirect:/goodBye";
            } else {
                return "redirect:/login";
            }
        }
    }

    @GetMapping("/goodBye")
    public String goodBye() {
        return "goodBye";
    }

    @GetMapping("/managerPage")
    public String managerPage(HttpSession session, @RequestParam(defaultValue = "1") int page, Model model) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getUserLevel() == 3) {
                Page<Member> members = memberService.selectAllMember(page - 1);
                model.addAttribute("members", members);

                return "managerPage";
            } else {
                return "redirect:/login";
            }
        }
    }

    @PostMapping("/changeUserLevel")
    public String changeUserLevel(HttpSession session, Member member) {
        MemberDto sessionInfo = (MemberDto) session.getAttribute("member");
        if (sessionInfo == null) {
            return "redirect:/login";
        } else {
            if (sessionInfo.getUserLevel() == 3) {
                memberService.changeUserLevel(member);
                return "redirect:/managerPage";
            } else {
                return "redirect:/login";
            }
        }
    }
}
