package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.constant.UserLevel;
import com.dgmoonlabs.psythinktank.domain.member.dto.*;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private static final String MEMBER_ID = "id";
    private static final String MEMBER_EMAIL = "email";
    private static final String MEMBER_PASSWORD = "password";
    public static final CheckEmailResponse CHECK_EMAIL_RESPONSE = CheckEmailResponse.from(true);
    public static final CheckIdResponse CHECK_ID_RESPONSE = CheckIdResponse.from(true);
    public static final FindIdResponse FIND_ID_RESPONSE = FindIdResponse.of(true, MEMBER_ID);
    public static final MemberUserLevelRequest MEMBER_USER_LEVEL_REQUEST = new MemberUserLevelRequest(MEMBER_ID, UserLevel.MANAGER.getLevel());
    private static final Member MEMBER_1 = Member.builder()
            .memberId(MEMBER_ID)
            .password(BCrypt.hashpw(MEMBER_PASSWORD, BCrypt.gensalt()))
            .email(MEMBER_EMAIL)
            .loginTryCount(1)
            .build();

    private static final Member MEMBER_2 = Member.builder()
            .memberId(MEMBER_ID)
            .password(BCrypt.hashpw(MEMBER_PASSWORD, BCrypt.gensalt()))
            .email(MEMBER_EMAIL)
            .loginTryCount(1)
            .build();
    private static final MemberResponse MEMBER_RESPONSE = MemberResponse.from(MEMBER_1);
    private static final List<Member> MEMBERS = List.of(MEMBER_1);
    private static final List<MemberResponse> MEMBER_RESPONSES = List.of(MEMBER_RESPONSE);
    private static final Page<Member> MEMBER_PAGE = new PageImpl<>(MEMBERS);
    private static final Page<MemberResponse> MEMBER_RESPONSE_PAGE = new PageImpl<>(MEMBER_RESPONSES);
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    void selectMembers() {
        when(memberRepository.findAll(any(PageRequest.class)))
                .thenReturn(MEMBER_PAGE);

        assertThat(memberService.selectMembers(PAGE_REQUEST))
                .isEqualTo(MEMBER_RESPONSE_PAGE);
    }

    @Test
    void getMember() {
        when(memberRepository.findByMemberId(anyString()))
                .thenReturn(Optional.of(MEMBER_1));

        assertThat(memberService.getMember(MEMBER_ID))
                .isEqualTo(MemberResponse.from(MEMBER_1));
    }

    @Test
    void selectMemberByEmail() {
        when(memberRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(MEMBER_1));

        assertThat(memberService.selectMemberByEmail(MEMBER_EMAIL))
                .isEqualTo(FIND_ID_RESPONSE);
    }

    @Test
    void changeUserLevel() {
        when(memberRepository.findByMemberId(anyString()))
                .thenReturn(Optional.of(MEMBER_2));

        memberService.changeUserLevel(MEMBER_USER_LEVEL_REQUEST);

        assertThat(MEMBER_2.getUserLevel())
                .isEqualTo(UserLevel.MANAGER.getLevel());
    }

    @Test
    void checkId() {
        when(memberRepository.findByMemberId(anyString()))
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
        when(memberRepository.findByMemberId(anyString()))
                .thenReturn(Optional.of(MEMBER_1));

        memberService.resetLoginTryCount(MEMBER_ID);

        assertThat(MEMBER_1.getLoginTryCount())
                .isZero();
    }

    @Test
    void increaseLoginTryCount() {
        when(memberRepository.findByMemberId(anyString()))
                .thenReturn(Optional.of(MEMBER_1));

        int loginTryCount = MEMBER_1.getLoginTryCount();

        memberService.increaseLoginTryCount(MEMBER_ID);

        assertThat(MEMBER_1.getLoginTryCount())
                .isEqualTo(loginTryCount + 1);
    }
}