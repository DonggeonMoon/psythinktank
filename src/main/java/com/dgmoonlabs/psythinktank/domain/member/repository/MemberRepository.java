package com.dgmoonlabs.psythinktank.domain.member.repository;

import com.dgmoonlabs.psythinktank.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByEmail(String email);

    Member findByEmailAndMemberId(String email, String memberId);
}
