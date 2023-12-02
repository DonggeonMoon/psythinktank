package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.dto.LoginRequest;
import com.dgmoonlabs.psythinktank.domain.member.dto.LoginResponse;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import com.dgmoonlabs.psythinktank.global.constant.LoginTry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.SESSION_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.LoginResult.*;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpSession session) {
        if (!"".equals(loginRequest.memberId()) && !"".equals(loginRequest.password())) {
            if (checkId(loginRequest.memberId())) {
                Member member = memberRepository.findById(loginRequest.password())
                        .orElseThrow(IllegalStateException::new);
                if (LoginTry.includes(member.getLoginTryCount())) {
                    if (checkPassword(loginRequest.memberId(), loginRequest.password())) {
                        member.setLoginTryCount(LoginTry.COUNT_RANGE.getStart());
                        session.setAttribute(SESSION_KEY.getText(),
                                memberRepository.findById(loginRequest.memberId())
                                        .orElseThrow(IllegalStateException::new).toDto());
                        return new LoginResponse(true, SUCCESS.getCode(), member.getLoginTryCount());
                    } else {
                        member.increaseLoginTryCount();
                        return new LoginResponse(false, WRONG_PASSWORD.getCode(), member.getLoginTryCount());
                    }
                } else {
                    return new LoginResponse(false, LOGIN_TRY_EXCEEDING.getCode(), null);
                }
            } else {
                new LoginResponse(false, ABSENT_ID.getCode(), null);
            }
        }
        return new LoginResponse(false, BLANK_ID_AND_PASSWORD.getCode(), null);
    }


    private boolean checkPassword(String memberId, String password) {
        return BCrypt.checkpw(password,
                memberRepository.findById(memberId)
                        .orElseThrow(IllegalStateException::new)
                        .getPassword());
    }

    private boolean checkId(String memberId) {
        return memberRepository.findById(memberId).isPresent();
    }
}
