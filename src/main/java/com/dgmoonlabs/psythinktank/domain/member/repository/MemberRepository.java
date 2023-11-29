package com.dgmoonlabs.psythinktank.domain.member.repository;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByMemberEmail(String memberEmail);

    Member findByMemberEmailAndMemberId(String memberEmail, String memberId);
}
