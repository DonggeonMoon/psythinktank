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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    private static final Member MEMBER_1 = Member.builder()
            .memberId("")
            .build();
    private static final List<Member> MEMBERS = List.of(MEMBER_1);
    private static final Page<Member> MEMBER_PAGE = new PageImpl<>(MEMBERS);


    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void selectMembers() {

        assertThat(memberService.selectMembers(PAGE_REQUEST))
                .isEqualTo(MEMBER_PAGE);
    }

    @Test
    void getMember() {
        assertThat(memberService.getMember(""))
                .isEqualTo(MemberResponse.from(MEMBER_1));
    }

    @Test
    void selectMemberByEmail() {
        assertThat(memberService.selectMemberByEmail(""))
                .isEqualTo(FindIdResponse.of(true, ""));
    }

    @Test
    void selectMemberByEmailAndMemberId() {
        assertThat(memberService.selectMemberByEmailAndMemberId(new FindPasswordRequest("", "")))
                .isEqualTo(FindPasswordResponse.from(true));
    }

    @Test
    void changeUserLevel() {
        memberService.changeUserLevel(new MemberRequest("", "", "", new Date(System.currentTimeMillis()), UserLevel.USER.getLevel()));

    }

    @Test
    void checkId() {
        assertThat(memberService.checkId(""))
                .isEqualTo(CheckIdResponse.from(true));
    }

    @Test
    void checkEmail() {
        assertThat(memberService.checkEmail(""))
                .isEqualTo(CheckEmailResponse.from(true));
    }

    @Test
    void resetLoginTryCount() {
        memberService.resetLoginTryCount("");
    }

    @Test
    void increaseLoginTryCount() {
        memberService.increaseLoginTryCount("");
    }
}