package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.dto.LoginRequest;
import com.dgmoonlabs.psythinktank.domain.member.dto.LoginResponse;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberResponse;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import com.dgmoonlabs.psythinktank.global.constant.LoginTry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.SESSION_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.LoginResult.*;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpSession session) {
        if ("".equals(loginRequest.memberId()) || "".equals(loginRequest.password())) {
            return new LoginResponse(false, BLANK_ID_AND_PASSWORD.getCode(), null);
        }
        Optional<Member> memberToCheck = memberRepository.findById(loginRequest.password());
        if (memberToCheck.isEmpty()) {
            return new LoginResponse(false, ABSENT_ID.getCode(), null);
        }
        Member member = memberToCheck.get();
        if (!LoginTry.includes(member.getLoginTryCount())) {
            return new LoginResponse(false, LOGIN_TRY_EXCEEDING.getCode(), null);
        }
        if (!checkPassword(loginRequest.memberId(), loginRequest.password())) {
            member.increaseLoginTryCount();
            return new LoginResponse(false, WRONG_PASSWORD.getCode(), member.getLoginTryCount());
        }
        member.setLoginTryCount(LoginTry.COUNT_RANGE.getStart());
        session.setAttribute(SESSION_KEY.getText(),
                MemberResponse.from(
                        memberRepository.findById(loginRequest.memberId())
                                .orElseThrow(IllegalStateException::new)
                ));
        return new LoginResponse(true, SUCCESS.getCode(), member.getLoginTryCount());
    }


    private boolean checkPassword(String memberId, String password) {
        return BCrypt.checkpw(password,
                memberRepository.findById(memberId)
                        .orElseThrow(IllegalStateException::new)
                        .getPassword());
    }
}
