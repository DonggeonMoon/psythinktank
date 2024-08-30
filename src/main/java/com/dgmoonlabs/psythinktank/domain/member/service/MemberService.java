package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.mail.service.MailService;
import com.dgmoonlabs.psythinktank.domain.member.dto.*;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import com.dgmoonlabs.psythinktank.global.constant.*;
import com.dgmoonlabs.psythinktank.global.exception.MemberNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createMember(MemberRequest memberRequest) {
        Member member = memberRequest.toEntity();
        member.changePassword(passwordEncoder.encode(memberRequest.password()));
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Page<MemberResponse> getMembers(Pageable pageable) {
        return memberRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(), Pagination.MEMBER_SIZE.getValue(),
                        Sort.by(CriteriaField.USER_LEVEL.getName())
                                .descending()
                                .and(Sort.by("memberId").ascending())
                )
        ).map(MemberResponse::from);
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(String memberId) {
        return MemberResponse.from(
                memberRepository.findByMemberId(memberId)
                        .orElseThrow(MemberNotExistException::new)
        );
    }

    @Transactional(readOnly = true)
    public FindIdResponse getMemberByEmail(String memberEmail) {
        Optional<Member> memberToFind = memberRepository.findByEmail(memberEmail);
        return memberToFind.map(
                member -> FindIdResponse.of(true, member.getMemberId())
        ).orElseGet(() ->
                FindIdResponse.of(false, null)
        );
    }

    @Transactional
    public FindPasswordResponse getMemberByEmailAndMemberId(FindPasswordRequest request) {
        Optional<Member> memberToFind = memberRepository.findByEmailAndMemberId(request.memberEmail(), request.memberId());

        if (memberToFind.isEmpty()) {
            return FindPasswordResponse.from(false);
        }

        memberToFind.ifPresent(member -> {
            StringBuilder stringBuilder = new StringBuilder();
            IntStream.range(0, TemporaryPassword.LENGTH.getValue())
                    .forEach(it -> stringBuilder.append((char) (random.nextInt(RandomNumber.BOUND.getValue()) + 'A')));
            String randomizedLetters = stringBuilder.toString();
            member.changePassword(passwordEncoder.encode(randomizedLetters));
            member.resetLoginTryCount();
            mailService.sendMail(mimeMessage -> {
                final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
                helper.setFrom(EmailForm.SENDER_ADDRESS.getText());
                helper.setTo(member.getEmail());
                helper.setSubject(EmailForm.TEMPORARY_PASSWORD_TITLE.getText());
                helper.setText(String.format(EmailForm.TEMPORARY_PASSWORD_TEXT.getText(), randomizedLetters), true);
            });
        });

        return FindPasswordResponse.from(true);
    }

    @Transactional
    public void updateMember(MemberRequest memberRequest) {
        Member memberToUpdate = memberRepository.findByMemberId(memberRequest.memberId())
                .orElseThrow(MemberNotExistException::new);
        memberToUpdate.changePassword(passwordEncoder.encode(memberRequest.password()));
        memberToUpdate.changeEmail(memberRequest.email());
    }

    @Transactional
    public void deleteMember(String memberId) {
        memberRepository.deleteByMemberId(memberId);
    }

    @Transactional
    public void changeUserLevel(MemberUserLevelRequest memberRequest) {
        memberRepository.findByMemberId(memberRequest.memberId())
                .orElseThrow(MemberNotExistException::new)
                .changeUserLevel(memberRequest.userLevel());
    }

    @Transactional(readOnly = true)
    public CheckIdResponse checkId(String memberId) {
        return CheckIdResponse.from(memberRepository.findByMemberId(memberId).isEmpty());
    }

    @Transactional(readOnly = true)
    public CheckEmailResponse checkEmail(String email) {
        return CheckEmailResponse.from(memberRepository.findByEmail(email).isEmpty());
    }

    @Transactional
    public void resetLoginTryCount(String memberId) {
        if (!StringUtils.hasText(memberId)) {
            return;
        }
        memberRepository.findByMemberId(memberId)
                .orElseThrow(MemberNotExistException::new)
                .resetLoginTryCount();
    }

    @Transactional
    public void increaseLoginTryCount(String memberId) {
        if (!StringUtils.hasText(memberId)) {
            return;
        }
        memberRepository.findByMemberId(memberId)
                .orElseGet(() -> Member.builder().build())
                .increaseLoginTryCount();
    }
}
