package com.dgmoonlabs.psythinktank.domain.member.service;

import com.dgmoonlabs.psythinktank.domain.member.dto.MemberDto;
import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import com.dgmoonlabs.psythinktank.domain.member.repository.MemberRepository;
import com.dgmoonlabs.psythinktank.global.constant.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.stream.IntStream;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.SESSION_KEY;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void join(Member member) {
        member.setPassword(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()));
        memberRepository.save(member);
    }

    @Transactional
    public Member getMemberInfo(HttpSession session) {
        return memberRepository.findById(((MemberDto) session.getAttribute(SESSION_KEY.getText())).getMemberId()).orElse(null);
    }

    @Transactional
    public void editMemberInfo(Member member) {
        Member newMember = memberRepository.findById(member.getMemberId()).orElse(Member.builder().build());
        newMember.setPassword(BCrypt.hashpw(member.getPassword(), BCrypt.gensalt()));
        newMember.setEmail(member.getEmail());
    }

    @Transactional
    public void deleteMemberInfo(String memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public Page<Member> selectAllMember(int page) {
        return memberRepository.findAll(PageRequest.of(page, Pagination.MEMBER_SIZE.getValue(), Sort.by(CriteriaField.USER_LEVEL.getName()).descending().and(Sort.by("memberId").ascending())));
    }

    @Transactional
    public Member selectOneMemberByEmail(String memberEmail) {
        return memberRepository.findByEmail(memberEmail);
    }

    @Transactional
    public Member selectOneMemberByEmailAndId(String memberEmail, String memberId) {
        return memberRepository.findByEmailAndMemberId(memberEmail, memberId);
    }

    @Transactional
    public void sendVerificationEmail(String email) {
        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            helper.setFrom(EmailForm.SENDER_ADDRESS.getText());
            helper.setTo(email);
            helper.setSubject(EmailForm.EMAIL_AUTHENTICATION_TITLE.getText());
            helper.setText("", true);
        };
        javaMailSender.send(preparator);
    }

    @Transactional
    public void sendTempPwEmail(String email) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        IntStream.range(0, TemporaryPassword.LENGTH.getValue())
                .forEach(it -> sb.append((char) (random.nextInt(RandomNumber.BOUND.getValue()) + 'A'))
                );
        String randomizedLetters = sb.toString();
        Member member = memberRepository.findByEmail(email);
        member.setPassword(BCrypt.hashpw(randomizedLetters, BCrypt.gensalt()));
        member.setLoginTryCount(LoginTry.COUNT_RANGE.getStart());
        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
            helper.setFrom(EmailForm.SENDER_ADDRESS.getText());
            helper.setTo(email);
            helper.setSubject(EmailForm.TEMPORARY_PASSWORD_TITLE.getText());
            helper.setText(String.format(EmailForm.TEMPORARY_PASSWORD_TEXT.getText(), randomizedLetters), true);
        };
        javaMailSender.send(preparator);
    }

    @Transactional
    public void changeUserLevel(Member member) {
        Member newMember = memberRepository.findById(member.getMemberId()).orElse(Member.builder().build());
        newMember.setUserLevel(member.getUserLevel());
    }
}
