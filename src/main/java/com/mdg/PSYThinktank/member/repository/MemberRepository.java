package com.mdg.PSYThinktank.member.repository;

import com.mdg.PSYThinktank.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByMemberEmail(String memberEmail);

    Member findByMemberEmailAndMemberId(String memberEmail, String memberId);
}
