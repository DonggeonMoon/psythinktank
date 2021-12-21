package com.mdg.PSYThinktank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdg.PSYThinktank.model.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
}
