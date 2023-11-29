package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    @Transactional
    public Map<String, Object> login(String memberId, String memberPw, HttpSession session, Model model) {
        Map<String, Object> map = new HashMap<>();
        if (!"".equals(memberId) && !"".equals(memberPw)) {
            if (checkId(memberId)) {
                Member member = memberRepository.findById(memberId).orElse(Member.builder().build());
                // 5회 이상 틀렸을 경우
                if (member.getLoginTryCount() < 5) {
                    if (checkPw(memberId, memberPw)) {
                        member.setLoginTryCount(0);
                        session.setAttribute("member",
                                memberRepository.findById(memberId).orElse(Member.builder().build()).toDto());
                        map.put("isSucceeded", true);
                        map.put("error", -1);
                    } else {
                        member.setLoginTryCount(member.getLoginTryCount() + 1);
                        map.put("isSucceeded", false);
                        map.put("error", 3);
                    }
                    map.put("loginTryCount", member.getLoginTryCount());
                } else {
                    map.put("isSucceeded", false);
                    map.put("error", 4);
                }
                return map;
            } else
                map.put("isSucceeded", false);
            map.put("error", 2);
            return map;
        }
        map.put("isSucceeded", false);
        map.put("error", 1);
        return map;
    }

    @Transactional
    public boolean checkId(String memberId) {
        return (memberRepository.findById(memberId).isPresent());
    }

    @Transactional
    public boolean checkPw(String memberId, String memberPw) {
        return BCrypt.checkpw(memberPw,
                memberRepository.findById(memberId).orElse(Member.builder().build()).getMemberPw());
    }

    @Transactional
    public boolean checkEmail(String memberEmail) {
        return (memberRepository.findByMemberEmail(memberEmail) != null);
    }
}
