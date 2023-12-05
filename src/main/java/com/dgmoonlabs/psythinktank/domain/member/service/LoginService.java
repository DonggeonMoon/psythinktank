package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.dto.LoginRequest;
import com.dgmoonlabs.psythinktank.domain.member.dto.LoginResponse;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import com.dgmoonlabs.psythinktank.global.constant.LoginTry;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static com.dgmoonlabs.psythinktank.global.constant.LoginResult.*;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpSession session) {
        if ("".equals(loginRequest.memberId()) || "".equals(loginRequest.memberPw())) {
            return new LoginResponse(false, BLANK_ID_AND_PASSWORD.getCode(), null);
        }
        Optional<Member> memberToCheck = memberRepository.findById(loginRequest.memberId());
        if (memberToCheck.isEmpty()) {
            return new LoginResponse(false, ABSENT_ID.getCode(), null);
        }
        Member member = memberToCheck.get();
        if (!LoginTry.includes(member.getLoginTryCount())) {
            return new LoginResponse(false, LOGIN_TRY_EXCEEDING.getCode(), null);
        }
        if (!checkPassword(loginRequest)) {
            member.increaseLoginTryCount();
            return new LoginResponse(false, WRONG_PASSWORD.getCode(), member.getLoginTryCount());
        }
        member.setLoginTryCount(LoginTry.COUNT_RANGE.getStart());
        return new LoginResponse(true, SUCCESS.getCode(), member.getLoginTryCount());
    }

    private boolean checkPassword(LoginRequest loginRequest) {
        return BCrypt.checkpw(
                loginRequest.memberPw(),
                memberRepository.findById(loginRequest.memberId())
                        .orElseThrow(IllegalStateException::new)
                        .getPassword()
        );
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        if (username.isBlank()) {
            throw new InternalAuthenticationServiceException(String.valueOf(BLANK_ID_AND_PASSWORD.getCode()));
        }
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new InternalAuthenticationServiceException(String.valueOf(ABSENT_ID.getCode())));

        if (member.isLocked()) {
            throw new InternalAuthenticationServiceException(String.valueOf(LOGIN_TRY_EXCEEDING.getCode()));
        }

        String memberId = member.getMemberId();
        String password = member.getPassword();
        List<SimpleGrantedAuthority> authorities = member.getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().getValue()))
                .toList();

        return new User(memberId, password, authorities);
    }
}
