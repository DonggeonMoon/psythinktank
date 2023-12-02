package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import com.dgmoonlabs.psythinktank.global.constant.LoginTry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.*;
import static com.dgmoonlabs.psythinktank.global.constant.LoginResult.*;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    @Transactional
    public Map<String, Object> login(String memberId, String password, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (!"".equals(memberId) && !"".equals(password)) {
            if (checkId(memberId)) {
                Member member = memberRepository.findById(memberId)
                        .orElseThrow(IllegalStateException::new);
                // 5회 이상 틀렸을 경우
                if (LoginTry.includes(member.getLoginTryCount())) {
                    if (checkPassword(memberId, password)) {
                        member.setLoginTryCount(LoginTry.COUNT_RANGE.getStart());
                        session.setAttribute(SESSION_KEY.getText(),
                                memberRepository.findById(memberId)
                                        .orElseThrow(IllegalStateException::new).toDto());
                        map.put(IS_SUCCEEDED_KEY.getText(), true);
                        map.put(ERROR_KEY.getText(), SUCCESS.getCode());
                    } else {
                        member.increaseLoginTryCount();
                        map.put(IS_SUCCEEDED_KEY.getText(), false);
                        map.put(ERROR_KEY.getText(), WRONG_PASSWORD.getCode());
                    }
                    map.put(LOGIN_TRY_COUNT_KEY.getText(), member.getLoginTryCount());
                } else {
                    map.put(IS_SUCCEEDED_KEY.getText(), false);
                    map.put(ERROR_KEY.getText(), LOGIN_TRY_EXCEEDING.getCode());
                }
                return map;
            } else
                map.put(IS_SUCCEEDED_KEY.getText(), false);
            map.put(ERROR_KEY.getText(), ABSENT_ID.getCode());
            return map;
        }
        map.put(IS_SUCCEEDED_KEY.getText(), false);
        map.put(ERROR_KEY.getText(), BLANK_ID_AND_PASSWORD.getCode());
        return map;
    }

    private boolean checkPassword(String memberId, String password) {
        return BCrypt.checkpw(password,
                memberRepository.findById(memberId)
                        .orElseThrow(IllegalStateException::new)
                        .getPassword());
    }

    public boolean checkId(String memberId) {
        return (memberRepository.findById(memberId).isPresent());
    }

    @Transactional
    public boolean checkEmail(String email) {
        return (memberRepository.findByEmail(email) != null);
    }
}
