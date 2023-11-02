package com.mdg.PSYThinktank.member.service;

import com.mdg.PSYThinktank.member.model.Member;
import com.mdg.PSYThinktank.member.repository.MemberRepository;
import java.util.Random;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void join(Member member) {
        member.setMemberPw(BCrypt.hashpw(member.getMemberPw(), BCrypt.gensalt()));
        memberRepository.save(member);
    }

    @Transactional
    public Member getMemberInfo(HttpSession session) {
        return memberRepository.findById(((Member) session.getAttribute("member")).getMemberId()).orElse(null);
    }

    @Transactional
    public void editMemberInfo(Member member) {
        Member newMember = memberRepository.findById(member.getMemberId()).orElse(Member.builder().build());
        newMember.setMemberPw(BCrypt.hashpw(member.getMemberPw(), BCrypt.gensalt()));
        newMember.setMemberEmail(member.getMemberEmail());
    }

    @Transactional
    public void deleteMemberInfo(String memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public Page<Member> selectAllMember(int page) {
        return memberRepository.findAll(PageRequest.of(page, 50, Sort.by("userLevel").descending().and(Sort.by("memberId").ascending())));
    }

    @Transactional
    public Member selectOneMemberByEmail(String memberEmail) {
        return memberRepository.findByMemberEmail(memberEmail);
    }

    @Transactional
    public Member selectOneMemberByEmailAndId(String memberEmail, String memberId) {
        return memberRepository.findByMemberEmailAndMemberId(memberEmail, memberId);
    }

    @Transactional
    public void sendVerificationEmail(String email) {
        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom("officialpsythinktank@gmail.com");
            helper.setTo(email);
            helper.setSubject("PSYThinktank 이메일 인증 메일입니다.");
            helper.setText("", true);
        };
        javaMailSender.send(preparator);
    }

    @Transactional
    public void sendTempPwEmail(String email) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 11; i++) {
            sb.append((char) (random.nextInt(57) + 'A'));
        }
        String randomizedStr = sb.toString();
        Member member = memberRepository.findByMemberEmail(email);
        member.setMemberPw(BCrypt.hashpw(randomizedStr, BCrypt.gensalt()));
        member.setLoginTryCount(0);
        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom("officialpsythinktank@gmail.com");
            helper.setTo(email);
            helper.setSubject("임시 비밀번호를 보내드립니다.");
            helper.setText("임시비밀번호는 " + randomizedStr + "입니다.", true);
        };
        javaMailSender.send(preparator);
    }

    @Transactional
    public void changeUserLevel(Member member) {
        Member newMember = memberRepository.findById(member.getMemberId()).orElse(Member.builder().build());
        newMember.setUserLevel(member.getUserLevel());
    }
}
