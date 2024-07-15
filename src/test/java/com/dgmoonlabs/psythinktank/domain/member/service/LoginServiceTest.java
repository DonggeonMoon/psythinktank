package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    private static final String MEMBER_ID = "id";
    private static final String MEMBER_PASSWORD = "password";
    private static final Member MEMBER = Member.builder()
            .memberId(MEMBER_ID)
            .password(BCrypt.hashpw(MEMBER_PASSWORD, BCrypt.gensalt()))
            .authorities(Set.of())
            .build();
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private LoginService loginService;

    @Test
    void loadUserByUsername() {
        when(memberRepository.findByMemberId(anyString()))
                .thenReturn(Optional.of(MEMBER));

        assertThat(loginService.loadUserByUsername(MEMBER_ID).getUsername())
                .isEqualTo(MEMBER_ID);
    }
}