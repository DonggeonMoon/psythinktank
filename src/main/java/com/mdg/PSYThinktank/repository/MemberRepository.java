package com.mdg.PSYThinktank.repository;

import com.mdg.PSYThinktank.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByMemberEmail(String memberEmail);

    Member findByMemberEmailAndMemberId(String memberEmail, String memberId);
}
