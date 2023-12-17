package com.dgmoonlabs.PSYThinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.dto.LoginRequest;
import com.dgmoonlabs.psythinktank.domain.member.dto.LoginResponse;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import com.dgmoonlabs.psythinktank.domain.member.service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    public static final LoginResponse LOGIN_RESPONSE = new LoginResponse(true, -1, 0);
    public static final String MEMBER_PASSWORD = "password";
    private static final String MEMBER_ID = "id";
    public static final LoginRequest LOGIN_REQUEST = new LoginRequest(MEMBER_ID, MEMBER_PASSWORD);
    private static final Member MEMBER = Member.builder()
            .memberId(MEMBER_ID)
            .password(BCrypt.hashpw(MEMBER_PASSWORD, BCrypt.gensalt()))
            .build();
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private LoginService loginService;

    @Test
    void login() {
        when(passwordEncoder.matches(any(CharSequence.class), anyString()))
                .thenReturn(true);
        when(memberRepository.findById(anyString()))
                .thenReturn(Optional.of(MEMBER));

        assertThat(loginService.login(LOGIN_REQUEST))
                .isEqualTo(LOGIN_RESPONSE);
    }
}