package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.mail.service.MailService;
import com.dgmoonlabs.psythinktank.domain.member.dto.*;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import com.dgmoonlabs.psythinktank.global.constant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MailService mailService;
    private final MemberRepository memberRepository;
    private final Random random;

    @Transactional
    public Page<Member> selectMembers(Pageable pageable) {
        return memberRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(), Pagination.MEMBER_SIZE.getValue(),
                        Sort.by(CriteriaField.USER_LEVEL.getName())
                                .descending()
                                .and(Sort.by("memberId").ascending())
                )
        );
    }

    @Transactional
    public MemberResponse getMember(String memberId) {
        return MemberResponse.from(
                memberRepository.findById(memberId)
                        .orElseThrow(IllegalStateException::new)
        );
    }

    @Transactional
    public void addMember(MemberRequest memberRequest) {
        Member member = memberRequest.toEntity();
        member.setPassword(BCrypt.hashpw(memberRequest.password(), BCrypt.gensalt()));
        memberRepository.save(member);
    }

    @Transactional
    public void editMember(MemberRequest memberRequest) {
        Member newMember = memberRepository.findById(memberRequest.memberId())
                .orElseThrow(IllegalArgumentException::new);
        newMember.setPassword(BCrypt.hashpw(memberRequest.password(), BCrypt.gensalt()));
        newMember.setEmail(memberRequest.email());
    }

    @Transactional
    public void deleteMember(String memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public FindIdResponse selectMemberByEmail(String memberEmail) {
        Optional<Member> memberToFind = memberRepository.findByEmail(memberEmail);
        return memberToFind.map(
                member -> FindIdResponse.of(true, member.getMemberId())
        ).orElseGet(() ->
                FindIdResponse.of(false, null)
        );
    }

    @Transactional
    public FindPasswordResponse selectMemberByEmailAndMemberId(FindPasswordRequest request) {
        Optional<Member> memberToFind = memberRepository.findByEmailAndMemberId(request.memberEmail(), request.memberId());
        if (memberToFind.isEmpty()) {
            return FindPasswordResponse.from(false);
        }
        Member member = memberToFind.get();

        StringBuilder stringBuilder = new StringBuilder();
        IntStream.range(0, TemporaryPassword.LENGTH.getValue())
                .forEach(it -> stringBuilder.append((char) (random.nextInt(RandomNumber.BOUND.getValue()) + 'A')));
        String randomizedLetters = stringBuilder.toString();
        member.setPassword(BCrypt.hashpw(randomizedLetters, BCrypt.gensalt()));
        member.setLoginTryCount(LoginTry.COUNT_RANGE.getStart());
        mailService.sendMail(mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            helper.setFrom(EmailForm.SENDER_ADDRESS.getText());
            helper.setTo(member.getEmail());
            helper.setSubject(EmailForm.TEMPORARY_PASSWORD_TITLE.getText());
            helper.setText(String.format(EmailForm.TEMPORARY_PASSWORD_TEXT.getText(), randomizedLetters), true);
        });

        return FindPasswordResponse.from(true);
    }

    @Transactional
    public void changeUserLevel(MemberRequest memberRequest) {
        Member newMember = memberRepository.findById(memberRequest.memberId())
                .orElseThrow(IllegalArgumentException::new);
        newMember.setUserLevel(memberRequest.userLevel());
    }

    @Transactional
    public CheckIdResponse checkId(String memberId) {
        return CheckIdResponse.from(memberRepository.findById(memberId).isEmpty());
    }

    @Transactional
    public CheckEmailResponse checkEmail(String email) {
        return CheckEmailResponse.from(memberRepository.findByEmail(email).isEmpty());
    }

    @Transactional
    public void resetLoginTryCount(String memberId) {
        if (memberId.isEmpty() || memberId.isBlank()) {
            return;
        }
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalStateException::new);
        member.setLoginTryCount(0);
    }

    @Transactional
    public void increaseLoginTryCount(String memberId) {
        if (memberId.isEmpty() || memberId.isBlank()) {
            return;
        }
        Member member = memberRepository.findById(memberId)
                .orElse(Member.builder().build());
        member.increaseLoginTryCount();
    }
}
