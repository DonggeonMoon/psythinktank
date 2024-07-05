package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.dgmoonlabs.psythinktank.global.constant.LoginResult.*;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        if (username.isBlank()) {
            throw new InternalAuthenticationServiceException(String.valueOf(BLANK_ID_AND_PASSWORD.getCode()));
        }
        Member member = memberRepository.findByMemberId(username)
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

        member.updateLastLoggedInAt();

        return new User(memberId, password, authorities);
    }
}
