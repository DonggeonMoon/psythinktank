package com.mdg.PSYThinktank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdg.PSYThinktank.model.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	Member findByMemberEmail(String memberEmail);

	Member findByMemberEmailAndMemberId(String memberEmail, String memberId);	
}
