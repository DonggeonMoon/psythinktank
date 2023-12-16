package com.dgmoonlabs.PSYThinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.constant.UserLevel;
import com.dgmoonlabs.psythinktank.domain.member.dto.*;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import com.dgmoonlabs.psythinktank.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    public static final CheckEmailResponse CHECK_EMAIL_RESPONSE = CheckEmailResponse.from(true);
    public static final CheckIdResponse CHECK_ID_RESPONSE = CheckIdResponse.from(true);
    private static final String MEMBER_ID = "id";
    public static final FindIdResponse FIND_ID_RESPONSE = FindIdResponse.of(true, MEMBER_ID);
    private static final String MEMBER_PASSWORD = "password";
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    private static final String MEMBER_EMAIL = "email";
    public static final MemberRequest MEMBER_REQUEST = new MemberRequest(MEMBER_ID, MEMBER_PASSWORD, MEMBER_EMAIL, new Date(System.currentTimeMillis()), UserLevel.MANAGER.getLevel());
    private static final Member MEMBER = Member.builder()
            .memberId(MEMBER_ID)
            .password(BCrypt.hashpw(MEMBER_PASSWORD, BCrypt.gensalt()))
            .email(MEMBER_EMAIL)
            .loginTryCount(1)
            .build();
    private static final List<Member> MEMBERS = List.of(MEMBER);
    private static final Page<Member> MEMBER_PAGE = new PageImpl<>(MEMBERS);
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    void selectMembers() {
        when(memberRepository.findAll(any(PageRequest.class)))
                .thenReturn(MEMBER_PAGE);

        assertThat(memberService.selectMembers(PAGE_REQUEST))
                .isEqualTo(MEMBER_PAGE);
    }

    @Test
    void getMember() {
        when(memberRepository.findById(anyString()))
                .thenReturn(Optional.of(MEMBER));

        assertThat(memberService.getMember(MEMBER_ID))
                .isEqualTo(MemberResponse.from(MEMBER));
    }

    @Test
    void selectMemberByEmail() {
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(MEMBER));

        assertThat(memberService.selectMemberByEmail(MEMBER_EMAIL))
                .isEqualTo(FIND_ID_RESPONSE);
    }

    @Test
    void changeUserLevel() {
        when(memberRepository.findById(anyString()))
                .thenReturn(Optional.of(MEMBER));

        memberService.changeUserLevel(MEMBER_REQUEST);

        assertThat(MEMBER.getUserLevel())
                .isEqualTo(UserLevel.MANAGER.getLevel());
    }

    @Test
    void checkId() {
        when(memberRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThat(memberService.checkId(MEMBER_ID))
                .isEqualTo(CHECK_ID_RESPONSE);
    }

    @Test
    void checkEmail() {
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThat(memberService.checkEmail(MEMBER_EMAIL))
                .isEqualTo(CHECK_EMAIL_RESPONSE);
    }

    @Test
    void resetLoginTryCount() {
        when(memberRepository.findById(anyString()))
                .thenReturn(Optional.of(MEMBER));

        memberService.resetLoginTryCount(MEMBER_ID);

        assertThat(MEMBER.getLoginTryCount())
                .isZero();
    }

    @Test
    void increaseLoginTryCount() {
        when(memberRepository.findById(anyString()))
                .thenReturn(Optional.of(MEMBER));

        int loginTryCount = MEMBER.getLoginTryCount();

        memberService.increaseLoginTryCount(MEMBER_ID);

        assertThat(MEMBER.getLoginTryCount())
                .isEqualTo(loginTryCount + 1);
    }
}